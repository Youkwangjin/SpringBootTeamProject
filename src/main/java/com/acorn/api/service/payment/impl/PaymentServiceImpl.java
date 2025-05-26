package com.acorn.api.service.payment.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.payment.KakaoPayReadyRequestDTO;
import com.acorn.api.dto.payment.KakaoPayReadyResponseDTO;
import com.acorn.api.dto.payment.PaymentRequestDTO;
import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.reservation.ReservationRepository;
import com.acorn.api.service.payment.PaymentService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient kakaoPayWebClient;
    private final ReservationRepository reservationRepository;

    @Value("${kakaopay.cid}")
    private String kakaoPayCid;

    @Value("${kakaopay.secret-key}")
    private String kakaoPaySecretKey;

    @Value("${kakaopay.ready-uri}")
    private String kakaoPayReadyUri;

    @Value("${kakaopay.approval-url}")
    private String kakaoPayApprovalUrl;

    @Value("${kakaopay.cancel-url}")
    private String kakaoPayCancelUrl;

    @Value("${kakaopay.fail-url}")
    private String kakaoPayFailUrl;

    private static final String KAKAO_AUTH_PREFIX = "SECRET_KEY ";

    @Override
    public KakaoPayReadyResponseDTO preparePayment(PaymentRequestDTO requestData) {
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
        if (!Objects.equals(currentUserId, reservationUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        final String containerName = reservationDetailData.getContainer().getContainerName();
        final Integer containerPrice = reservationDetailData.getContainer().getContainerPrice();

        KakaoPayReadyRequestDTO kakaoRequest = KakaoPayReadyRequestDTO.builder()
                .cid(kakaoPayCid)
                .partner_order_id(reservationId.toString())
                .partner_user_id(reservationUserId.toString())
                .item_name(containerName)
                .quantity(1)
                .total_amount(containerPrice)
                .tax_free_amount(0)
                .approval_url(kakaoPayApprovalUrl)
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
}