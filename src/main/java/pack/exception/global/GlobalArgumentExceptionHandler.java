package pack.exception.global;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pack.api.code.common.ApiValidationErrorCode;
import pack.api.response.ApiErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalArgumentExceptionHandler {

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        log.error(" =========================== IllegalArgumentException: {} =========================== ", ex.getMessage());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .errorStatus(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR.getErrorStatus())
                .errorDivisionCode(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR.getErrorDivisionCode())
                .errorMsg(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR.getErrorMsg())
                .build();

        return ResponseEntity
                .status(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR.getErrorStatus())
                .body(response);
    }
}
