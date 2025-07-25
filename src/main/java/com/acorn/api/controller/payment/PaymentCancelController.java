package com.acorn.api.controller.payment;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.payment.request.PaymentCancelReqDTO;
import com.acorn.api.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentCancelController {

    private final PaymentService paymentService;

    @PatchMapping("/api/user/payments/cancel/{paymentId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelPayment(@Valid @RequestBody PaymentCancelReqDTO requestData) {
        paymentService.cancelPayment(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.PAYMENT_CANCEL_SUCCESS);
    }
}