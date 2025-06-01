package com.acorn.api.controller.payment;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.payment.PaymentCancelRequestDTO;
import com.acorn.api.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentCancelController {

    private final PaymentService paymentService;

    @PatchMapping("/api/user/payments/cancel/{paymentId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelPayment(@Valid @RequestBody PaymentCancelRequestDTO requestData) {
        log.info(" *****************************    User Payment Cancel START    *****************************");

        paymentService.cancelPayment(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.PAYMENT_CANCEL_SUCCESS);
    }
}