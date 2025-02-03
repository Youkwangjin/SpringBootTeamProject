package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {

        log.error(" =========================== AuthenticationException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(ApiHttpErrorCode.AUTHENTICATION_ERROR.getHttpStatus())
                .errorDivisionCode(ApiHttpErrorCode.AUTHENTICATION_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.AUTHENTICATION_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.AUTHENTICATION_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        log.error(" =========================== AccessDeniedException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(ApiHttpErrorCode.FORBIDDEN_ERROR.getHttpStatus())
                .errorDivisionCode(ApiHttpErrorCode.FORBIDDEN_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.FORBIDDEN_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.FORBIDDEN_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(AcontainerException.class)
    public ResponseEntity<ApiErrorResponse> handleAcontainerException(AcontainerException ex, HttpServletRequest request) {
        log.error(" =========================== AcontainerException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(ex.getHttpStatus())
                .errorDivisionCode(ex.getErrorDivisionCode())
                .errorMsg(ex.getErrorMsg())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {

        log.error(" =========================== Unexpected Exception: {} =========================== ", ex.getMessage(), ex);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .errorDivisionCode(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }
}