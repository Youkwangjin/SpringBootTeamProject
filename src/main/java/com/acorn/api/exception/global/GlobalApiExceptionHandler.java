package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalApiExceptionHandler {

    @ExceptionHandler(AcontainerException.class)
    public ResponseEntity<ApiErrorResponse> handleAcontainerException(AcontainerException acontainerException) {
        log.error(" =========================== AcontainerException: {} =========================== ", acontainerException.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(acontainerException.getHttpStatus())
                .errorDivisionCode(acontainerException.getErrorDivisionCode())
                .errorMsg(acontainerException.getErrorMsg())
                .build();

        return ResponseEntity.status(acontainerException.getHttpStatus()).body(response);
    }

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

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex) {
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
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception ex) {

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