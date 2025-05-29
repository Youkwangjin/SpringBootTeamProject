package com.acorn.api.service.payment.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.payment.PaymentStatus;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.dto.payment.*;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.payment.Payment;
import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.payment.PaymentRepository;
import com.acorn.api.repository.reservation.ReservationRepository;
import com.acorn.api.service.payment.PaymentService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient kakaoPayWebClient;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final ContainerRepository containerRepository;

    @Value("${kakaopay.cid}")
    private String kakaoPayCid;

    @Value("${kakaopay.secret-key}")
    private String kakaoPaySecretKey;

    @Value("${kakaopay.ready-uri}")
    private String kakaoPayReadyUri;

    @Value("${kakaopay.approve-uri}")
    private String kakaoPayApprovalUri;

    @Value("${kakaopay.approval-url}")
    private String kakaoPayApprovalUrl;

    @Value("${kakaopay.cancel-url}")
    private String kakaoPayCancelUrl;

    @Value("${kakaopay.fail-url}")
    private String kakaoPayFailUrl;

    private static final String KAKAO_AUTH_PREFIX = "SECRET_KEY ";

    @Override
    @Transactional
    public KakaoPayReadyResponseDTO preparePayment(PaymentReadyRequestDTO requestData) {
        final Integer reservationId = requestData.getReservationId();
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();

        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Reservation reservationDetailData = reservationRepository.selectReservationDetailData(reservationId);
        if (reservationDetailData == null) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_NOT_FOUND);
        }

        final Integer reservationUserId = reservationDetailData.getReservationUserId();
        final String containerName = reservationDetailData.getContainer().getContainerName();
        final Integer containerPrice = reservationDetailData.getContainer().getContainerPrice();
        final Integer currentReservationStatus = reservationDetailData.getReservationStatus();
        final Integer reservationCancelStatus = ReservationStatus.RESERVATION_STATUS_CANCELLED.getCode();

        if (!Objects.equals(currentUserId, reservationUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (Objects.equals(currentReservationStatus, reservationCancelStatus)) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_DENIED_ON_CANCELED);
        }

        KakaoPayReadyRequestDTO kakaoRequest = KakaoPayReadyRequestDTO.builder()
                .cid(kakaoPayCid)
                .partner_order_id(reservationId.toString())
                .partner_user_id(reservationUserId.toString())
                .item_name(containerName)
                .quantity(1)
                .total_amount(containerPrice)
                .tax_free_amount(0)
                .approval_url(kakaoPayApprovalUrl + "?reservationId=" + reservationId)
                .cancel_url(kakaoPayCancelUrl)
                .fail_url(kakaoPayFailUrl)
                .build();

        KakaoPayReadyResponseDTO response = kakaoPayWebClient.post()
                .uri(kakaoPayReadyUri)
                .header(HttpHeaders.AUTHORIZATION, KAKAO_AUTH_PREFIX + kakaoPaySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(kakaoRequest)
                .retrieve()
                .bodyToMono(KakaoPayReadyResponseDTO.class)
                .block();

        if (response == null || response.getTid() == null) {
            throw new AcontainerException(ApiErrorCode.KAKAOPAY_API_FAILED);
        }

        final String kakaoPayTid = response.getTid();
        reservationRepository.updateReservationTid(reservationId, kakaoPayTid);

        return response;
    }

    @Override
    @Transactional
    public KakaoPayApproveResponseDTO approvePayment(PaymentApproveRequestDTO requestData) {
        final Integer reservationId = requestData.getReservationId();
        final String pgToken = requestData.getPg_token();
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();

        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Reservation reservationDetailData = reservationRepository.selectReservationDetailData(reservationId);
        if (reservationDetailData == null) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_NOT_FOUND);
        }

        final Integer reservationUserId = reservationDetailData.getReservationUserId();
        final String currentTid = reservationDetailData.getReservationTid();
        final Integer containerId = reservationDetailData.getContainer().getContainerId();
        final Integer containerPrice = reservationDetailData.getContainer().getContainerPrice();

        if (!Objects.equals(currentUserId, reservationUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Payment paymentDetailData = paymentRepository.selectPaymentByReservationId(reservationId);
        if (paymentDetailData != null) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_ALREADY_COMPLETED);
        }

        final Integer containerUseStatus = ContainerStatus.CONTAINER_STATUS_USE.getCode();
        final Integer reservationActiveStatus = ReservationStatus.RESERVATION_STATUS_ACTIVE.getCode();
        final Integer paymentCompletedStatus = PaymentStatus.PAYMENT_STATUS_COMPLETED.getCode();

        KakaoPayApproveRequestDTO kakaoRequest = KakaoPayApproveRequestDTO.builder()
                .cid(kakaoPayCid)
                .tid(currentTid)
                .partner_order_id(reservationId.toString())
                .partner_user_id(currentUserId.toString())
                .pg_token(pgToken)
                .build();

        KakaoPayApproveResponseDTO response = kakaoPayWebClient.post()
                .uri(kakaoPayApprovalUri)
                .header(HttpHeaders.AUTHORIZATION, KAKAO_AUTH_PREFIX + kakaoPaySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(kakaoRequest)
                .retrieve()
                .bodyToMono(KakaoPayApproveResponseDTO.class)
                .block();

        Container updateContainerStatus = Container.builder()
                .containerId(containerId)
                .containerStatus(containerUseStatus)
                .build();

        containerRepository.updateContainerStatus(updateContainerStatus);

        Reservation updateReservationStatus = Reservation.builder()
                .reservationId(reservationId)
                .reservationStatus(reservationActiveStatus)
                .build();

        reservationRepository.updateReservationStatus(updateReservationStatus);

        final Integer paymentId = paymentRepository.selectPaymentIdKey();
        Payment newPayment = Payment.builder()
                .paymentId(paymentId)
                .paymentTid(currentTid)
                .paymentUserId(currentUserId)
                .paymentReservationId(reservationId)
                .paymentAmount(containerPrice)
                .paymentStatus(paymentCompletedStatus)
                .build();

        paymentRepository.insertPayment(newPayment);

        return response;
    }
}