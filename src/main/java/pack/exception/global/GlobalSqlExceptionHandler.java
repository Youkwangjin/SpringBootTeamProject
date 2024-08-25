package pack.exception.global;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pack.api.code.common.ApiErrorCode;
import pack.api.response.ApiErrorResponse;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;


@Slf4j
@RestControllerAdvice
public class GlobalSqlExceptionHandler {

    // SQLSyntaxErrorException
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {

        log.error(" =========================== SQLSyntaxErrorException: {} =========================== ", ex.getMessage(), ex);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .errorStatus(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorStatus())
                .errorDivisionCode(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorDivisionCode())
                .errorMsg(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorStatus())
                .body(response);
    }

    // SQLIntegrityConstraintViolationException
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {

        log.error(" =========================== SQLIntegrityConstraintViolationException: {} =========================== ", ex.getMessage(), ex);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .errorStatus(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorStatus())
                .errorDivisionCode(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorDivisionCode())
                .errorMsg(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiErrorCode.INTERNAL_SERVER_ERROR.getErrorStatus())
                .body(response);
    }


}
