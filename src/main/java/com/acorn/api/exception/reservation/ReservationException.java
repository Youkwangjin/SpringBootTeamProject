package com.acorn.api.exception.reservation;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.reservation.ReservationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {ReservationController.class})
public class ReservationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorReservationMsg = new StringBuilder();
        ApiValidationErrorCode reservationErrorCode = ApiValidationErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorReservationMsg.isEmpty()) {
                errorReservationMsg.append(", ");
            }
            errorReservationMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            reservationErrorCode = switch (fieldError.getField()) {
                case "reservationId", "reservationContainerId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                default -> ApiValidationErrorCode.VALIDATION_ERROR;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorReservationMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                reservationErrorCode.getHttpStatus(),
                reservationErrorCode.getErrorDivisionCode(),
                reservationErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(reservationErrorCode.getHttpStatus()).body(response);
    }
}