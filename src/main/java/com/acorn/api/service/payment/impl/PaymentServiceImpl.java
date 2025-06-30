package com.acorn.api.service.payment.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.payment.PaymentStatus;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.dto.payment.kakaopay.request.KakaoPayApproveReqDTO;
import com.acorn.api.dto.payment.kakaopay.request.KakaoPayCancelReqDTO;
import com.acorn.api.dto.payment.kakaopay.request.KakaoPayReadyReqDTO;
import com.acorn.api.dto.payment.kakaopay.response.KakaoPayApproveResDTO;
import com.acorn.api.dto.payment.kakaopay.response.KakaoPayCancelResDTO;
import com.acorn.api.dto.payment.kakaopay.response.KakaoPayReadyResDTO;
import com.acorn.api.dto.payment.request.PaymentApproveReqDTO;
import com.acorn.api.dto.payment.request.PaymentCancelReqDTO;
import com.acorn.api.dto.payment.request.PaymentListReqDTO;
import com.acorn.api.dto.payment.request.PaymentReadyReqDTO;
import com.acorn.api.dto.payment.response.PaymentDetailResDTO;
import com.acorn.api.dto.payment.response.PaymentListResDTO;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Value("${kakaopay.cancel-uri}")
    private String kakaoPayCancelUri;

    @Value("${kakaopay.approval-url}")
    private String kakaoPayApprovalUrl;

    @Value("${kakaopay.cancel-url}")
    private String kakaoPayCancelUrl;

    @Value("${kakaopay.fail-url}")
    private String kakaoPayFailUrl;

    private static final String KAKAO_AUTH_PREFIX = "SECRET_KEY ";

    @Override
    public List<PaymentListResDTO> getPaymentListData(PaymentListReqDTO listData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        listData.setPaymentUserId(currentUserId);
        listData.setTotalCount(paymentRepository.selectListCountByRequest(listData));
        List<Payment> paymentListData = paymentRepository.selectPaymentListData(listData);

        return paymentListData.stream()
                .map(paymentList -> {
                    final Integer rowNum = paymentList.getRowNum();
                    final Integer paymentId = paymentList.getPaymentId();
                    final Integer paymentUserId = paymentList.getPaymentUserId();
                    final Integer paymentAmount = paymentList.getPaymentAmount();
                    final Integer paymentStatus = paymentList.getPaymentStatus();
                    final String containerName = paymentList.getReservation().getContainer().getContainerName();
                    final LocalDateTime paymentApproved = paymentList.getPaymentApproved();
                    final LocalDateTime paymentCanceled = paymentList.getPaymentCanceled();

                    return PaymentListResDTO.builder()
                            .rowNum(rowNum)
                            .paymentId(paymentId)
                            .paymentUserId(paymentUserId)
                            .paymentAmount(paymentAmount)
                            .paymentStatus(paymentStatus)
                            .containerName(containerName)
                            .paymentApproved(paymentApproved)
                            .paymentCanceled(paymentCanceled)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDetailResDTO getPaymentData(Integer paymentId) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Payment paymentData = paymentRepository.selectPaymentDetailData(paymentId);
        if (paymentData == null) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_NOT_FOUND);
        }

        final String paymentTid = paymentData.getPaymentTid();
        final Integer paymentUserId = paymentData.getPaymentUserId();
        final Integer paymentAmount = paymentData.getPaymentAmount();
        final Integer paymentStatus = paymentData.getPaymentStatus();
        final String containerName = paymentData.getReservation().getContainer().getContainerName();
        final LocalDateTime paymentApproved = paymentData.getPaymentApproved();
        final LocalDateTime paymentCanceled = paymentData.getPaymentCanceled();
        final LocalDateTime paymentCancelDeadline = paymentData.getPaymentCancelDeadline();

        if (!Objects.equals(currentUserId, paymentUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        return PaymentDetailResDTO.builder()
                .paymentId(paymentId)
                .paymentTid(paymentTid)
                .paymentUserId(paymentUserId)
                .paymentAmount(paymentAmount)
                .paymentStatus(paymentStatus)
                .containerName(containerName)
                .paymentApproved(paymentApproved)
                .paymentCanceled(paymentCanceled)
                .paymentCancelDeadline(paymentCancelDeadline)
                .build();
    }

    @Override
    @Transactional
    public KakaoPayReadyResDTO preparePayment(PaymentReadyReqDTO requestData) {
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

        KakaoPayReadyReqDTO kakaoRequest = KakaoPayReadyReqDTO.builder()
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

        KakaoPayReadyResDTO response = kakaoPayWebClient.post()
                .uri(kakaoPayReadyUri)
                .header(HttpHeaders.AUTHORIZATION, KAKAO_AUTH_PREFIX + kakaoPaySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(kakaoRequest)
                .retrieve()
                .bodyToMono(KakaoPayReadyResDTO.class)
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
    public KakaoPayApproveResDTO approvePayment(PaymentApproveReqDTO requestData) {
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
        if (paymentDetailData == null) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_NOT_FOUND);
        }

        final Integer paymentId = paymentDetailData.getPaymentId();
        final Integer containerUseStatus = ContainerStatus.CONTAINER_STATUS_USE.getCode();
        final Integer reservationActiveStatus = ReservationStatus.RESERVATION_STATUS_ACTIVE.getCode();
        final Integer paymentCompletedStatus = PaymentStatus.PAYMENT_STATUS_COMPLETED.getCode();

        KakaoPayApproveReqDTO kakaoRequest = KakaoPayApproveReqDTO.builder()
                .cid(kakaoPayCid)
                .tid(currentTid)
                .partner_order_id(reservationId.toString())
                .partner_user_id(currentUserId.toString())
                .pg_token(pgToken)
                .build();

        KakaoPayApproveResDTO response = kakaoPayWebClient.post()
                .uri(kakaoPayApprovalUri)
                .header(HttpHeaders.AUTHORIZATION, KAKAO_AUTH_PREFIX + kakaoPaySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(kakaoRequest)
                .retrieve()
                .bodyToMono(KakaoPayApproveResDTO.class)
                .block();

        Container updateContainerStatus = Container.builder()
                .containerId(containerId)
                .containerStatus(containerUseStatus)
                .build();

        containerRepository.updateContainerStatus(updateContainerStatus);

        final LocalDateTime reservationStartDate = LocalDateTime.now();
        final LocalDateTime reservationEndDate = reservationStartDate.plusMonths(1).with(LocalTime.NOON);

        Reservation updateReservation = Reservation.builder()
                .reservationId(reservationId)
                .reservationStatus(reservationActiveStatus)
                .reservationStartDate(reservationStartDate)
                .reservationEndDate(reservationEndDate)
                .build();

        reservationRepository.updateReservation(updateReservation);

        final LocalDateTime paymentApproved = LocalDateTime.now();
        final LocalDateTime paymentCancelDeadline = paymentApproved.plusDays(3);

        Payment updatePayment = Payment.builder()
                .paymentId(paymentId)
                .paymentTid(currentTid)
                .paymentAmount(containerPrice)
                .paymentStatus(paymentCompletedStatus)
                .paymentApproved(paymentApproved)
                .paymentCancelDeadline(paymentCancelDeadline)
                .build();

        paymentRepository.updatePayment(updatePayment);

        return response;
    }

    @Override
    @Transactional
    public void cancelPayment(PaymentCancelReqDTO requestData) {
        final Integer paymentId = requestData.getPaymentId();
        final String paymentTid = requestData.getPaymentTid();
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();

        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Payment paymentData = paymentRepository.selectPaymentDetailData(paymentId);
        if (paymentData == null) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_NOT_FOUND);
        }

        final String currentTid = paymentData.getPaymentTid();
        final Integer paymentUserId = paymentData.getPaymentUserId();
        final Integer paymentAmount = paymentData.getPaymentAmount();
        final Integer paymentStatus = paymentData.getPaymentStatus();
        final LocalDateTime paymentCancelDeadline = paymentData.getPaymentCancelDeadline();
        final Integer reservationId = paymentData.getReservation().getReservationId();
        final Integer containerId = paymentData.getReservation().getContainer().getContainerId();
        final Integer paymentCompletedStatus = PaymentStatus.PAYMENT_STATUS_COMPLETED.getCode();
        final Integer paymentCancelStatus = PaymentStatus.PAYMENT_STATUS_CANCELLED.getCode();
        final Integer reservationPendingStatus = ReservationStatus.RESERVATION_STATUS_PENDING.getCode();
        final Integer containerPendingStatus = ContainerStatus.CONTAINER_STATUS_PENDING.getCode();

        if (!Objects.equals(currentUserId, paymentUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentTid, paymentTid)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(paymentStatus, paymentCompletedStatus)) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_CANCEL_ERROR);
        }

        if (paymentCancelDeadline == null || paymentCancelDeadline.isBefore(LocalDateTime.now())) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_CANCEL_EXPIRED);
        }

        KakaoPayCancelReqDTO kakaoRequest = KakaoPayCancelReqDTO.builder()
                .cid(kakaoPayCid)
                .tid(currentTid)
                .cancel_amount(paymentAmount)
                .cancel_tax_free_amount(0)
                .cancel_available_amount(paymentAmount)
                .build();

        KakaoPayCancelResDTO response = kakaoPayWebClient.post()
                .uri(kakaoPayCancelUri)
                .header(HttpHeaders.AUTHORIZATION, KAKAO_AUTH_PREFIX + kakaoPaySecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(kakaoRequest)
                .retrieve()
                .bodyToMono(KakaoPayCancelResDTO.class)
                .block();

        if (response == null) {
            throw new AcontainerException(ApiErrorCode.KAKAOPAY_CANCEL_FAILED);
        }

        Payment updatePaymentStatus = Payment.builder()
                .paymentId(paymentId)
                .paymentStatus(paymentCancelStatus)
                .build();

        paymentRepository.updatePaymentStatus(updatePaymentStatus);

        Reservation updateReservationStatus = Reservation.builder()
                .reservationId(reservationId)
                .reservationStatus(reservationPendingStatus)
                .build();

        reservationRepository.updateReservationStatus(updateReservationStatus);

        Container updateContainerStatus = Container.builder()
                .containerId(containerId)
                .containerStatus(containerPendingStatus)
                .build();

        containerRepository.updateContainerStatus(updateContainerStatus);
    }
}