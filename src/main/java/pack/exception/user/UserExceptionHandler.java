package pack.exception.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pack.api.response.ApiErrorResponse;
import pack.api.code.user.ApiUserErrorCode;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorUserMsg = new StringBuilder();

        ApiUserErrorCode userErrorCode = ApiUserErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorUserMsg.isEmpty()) {
                errorUserMsg.append(", ");
            }
            errorUserMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            userErrorCode = switch (fieldError.getField()) {
                case "userEmail" -> ApiUserErrorCode.EMAIL_FORMAT_ERROR;
                case "userPassword" -> ApiUserErrorCode.PASSWORD_STRENGTH_ERROR;
                case "userName" -> ApiUserErrorCode.NAME_FORMAT_ERROR;
                case "userTel" -> ApiUserErrorCode.TELEPHONE_FORMAT_ERROR;
                default -> userErrorCode;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorUserMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                userErrorCode.getUserErrorStatus(),
                userErrorCode.getUserErrorDivisionCode(),
                userErrorCode.getUserErrorMsg()
        );
        return ResponseEntity.status(userErrorCode.getUserErrorStatus()).body(response);
    }
}
