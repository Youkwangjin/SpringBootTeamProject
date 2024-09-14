package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHttpExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalHttpExceptionHandler.class);

    // 401 (AuthenticationException)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> authenticationException(AuthenticationException ex) {

        log.error(" =========================== AuthenticationException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .errorStatus(ApiHttpErrorCode.AUTHENTICATION_ERROR.getErrorStatus())
                .errorDivisionCode(ApiHttpErrorCode.AUTHENTICATION_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.AUTHENTICATION_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.AUTHENTICATION_ERROR.getErrorStatus())
                .body(response);
    }

    // 403 (AccessDeniedException)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> accessDeniedException(AccessDeniedException ex) {

        log.error(" =========================== AccessDeniedException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .errorStatus(ApiHttpErrorCode.FORBIDDEN_ERROR.getErrorStatus())
                .errorDivisionCode(ApiHttpErrorCode.FORBIDDEN_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.FORBIDDEN_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.FORBIDDEN_ERROR.getErrorStatus())
                .body(response);
    }
}
