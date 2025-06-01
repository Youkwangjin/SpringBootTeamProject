package com.acorn.api.exception.payment;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.payment.PaymentCancelController;
import com.acorn.api.controller.payment.PaymentReadyController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice (assignableTypes = {PaymentReadyController.class, PaymentCancelController.class})
public class PaymentExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorPaymentMsg = new StringBuilder();
        ApiValidationErrorCode paymentErrorCode = ApiValidationErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorPaymentMsg.isEmpty()) {
                errorPaymentMsg.append(", ");
            }
            errorPaymentMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            paymentErrorCode = switch (fieldError.getField()) {
                case "paymentId" -> ApiValidationErrorCode.PAYMENT_ID_ERROR;
                case "paymentTid" -> ApiValidationErrorCode.PAYMENT_TID_ERROR;
                case "reservationId" -> ApiValidationErrorCode.RESERVATION_ID_ERROR;
                case "pgToken" -> ApiValidationErrorCode.TOKEN_VALUE_ERROR;
                default -> ApiValidationErrorCode.VALIDATION_ERROR;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorPaymentMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                paymentErrorCode.getHttpStatus(),
                paymentErrorCode.getErrorDivisionCode(),
                paymentErrorCode.getErrorMsg()
        );

        return ResponseEntity.status(paymentErrorCode.getHttpStatus()).body(response);
    }
}