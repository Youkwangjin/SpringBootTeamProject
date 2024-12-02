package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalSqlExceptionHandler {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {

        log.error(" =========================== SQLSyntaxErrorException: {} =========================== ", ex.getMessage(), ex);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .httpStatus(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .errorDivisionCode(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {

        log.error(" =========================== SQLIntegrityConstraintViolationException: {} =========================== ", ex.getMessage(), ex);

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