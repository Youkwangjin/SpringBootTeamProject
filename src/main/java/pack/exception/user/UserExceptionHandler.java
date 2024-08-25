package pack.exception.user;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pack.api.code.common.ApiErrorCode;
import pack.api.response.ApiErrorResponse;


@RestControllerAdvice
public class UserExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

         log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorUserMsg = new StringBuilder();

        ApiErrorCode userErrorCode = ApiErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorUserMsg.isEmpty()) {
                errorUserMsg.append(", ");
            }
            errorUserMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            userErrorCode = switch (fieldError.getField()) {
                case "userEmail" -> ApiErrorCode.EMAIL_FORMAT_ERROR;
                case "userPassword" -> ApiErrorCode.PASSWORD_STRENGTH_ERROR;
                case "userDisplayName" -> ApiErrorCode.NAME_FORMAT_ERROR;
                case "userTel" -> ApiErrorCode.TELEPHONE_FORMAT_ERROR;
                default -> userErrorCode;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorUserMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                userErrorCode.getErrorStatus(),
                userErrorCode.getErrorDivisionCode(),
                userErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(userErrorCode.getErrorStatus()).body(response);
    }
}
