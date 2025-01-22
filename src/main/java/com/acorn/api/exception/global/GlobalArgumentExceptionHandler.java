package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalArgumentExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        log.error(" =========================== IllegalArgumentException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(ApiHttpErrorCode.BAD_REQUEST_ERROR.getHttpStatus())
                .errorDivisionCode(ApiHttpErrorCode.BAD_REQUEST_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.BAD_REQUEST_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.BAD_REQUEST_ERROR.getHttpStatus())
                .body(response);
    }
}