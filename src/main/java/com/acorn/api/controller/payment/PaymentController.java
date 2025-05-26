package com.acorn.api.controller.payment;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.payment.KakaoPayReadyResponseDTO;
import com.acorn.api.dto.payment.PaymentRequestDTO;
import com.acorn.api.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/user/payments/ready/{reservationId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userPayment(@RequestBody PaymentRequestDTO requestData) {
        log.info(" *****************************    User Payment START    *****************************");

        KakaoPayReadyResponseDTO response = paymentService.preparePayment(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.KAKAOPAY_READY_SUCCESS, response);
    }
}