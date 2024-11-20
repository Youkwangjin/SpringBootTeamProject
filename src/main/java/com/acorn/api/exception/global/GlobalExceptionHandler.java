package com.acorn.api.exception.global;

import com.acorn.api.code.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AcontainerException.class)
    public ResponseEntity<ApiErrorResponse> handleAcontainerException(AcontainerException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .httpStatus(ex.getHttpStatus())
                .errorDivisionCode(ex.getErrorDivisionCode())
                .errorMsg(ex.getErrorMsg())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
}