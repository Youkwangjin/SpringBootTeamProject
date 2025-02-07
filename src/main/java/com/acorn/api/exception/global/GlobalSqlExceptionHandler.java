package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class GlobalSqlExceptionHandler {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Object handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {
        log.error(" =========================== SQLSyntaxErrorException: {} =========================== ", ex.getMessage(), ex);
        return handleException();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Object handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        log.error(" =========================== SQLIntegrityConstraintViolationException: {} =========================== ", ex.getMessage(), ex);
        return handleException();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Object handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error(" =========================== DataIntegrityViolationException: {} =========================== ", ex.getMessage(), ex);
        return handleException();
    }

    private Object handleException() {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .httpStatus(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .errorDivisionCode(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getErrorDivisionCode())
                .errorMsg(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getErrorMsg())
                .build();
        return ResponseEntity.status(ApiHttpErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(errorResponse);
    }
}