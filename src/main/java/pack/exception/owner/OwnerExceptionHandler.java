package pack.exception.owner;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pack.api.code.common.ApiValidationErrorCode;
import pack.api.response.ApiErrorResponse;
import pack.controller.owner.OwnerRegisterController;


@RestControllerAdvice(basePackageClasses  = OwnerRegisterController.class)
public class OwnerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(OwnerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

         log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorOwnerMsg = new StringBuilder();

        ApiValidationErrorCode ownerErrorCode = ApiValidationErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorOwnerMsg.isEmpty()) {
                errorOwnerMsg.append(", ");
            }
            errorOwnerMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            ownerErrorCode = switch (fieldError.getField()) {
                case "ownerEmail" -> ApiValidationErrorCode.EMAIL_FORMAT_ERROR;
                case "ownerBusinessNum" -> ApiValidationErrorCode.BUSINESS_NUMBER_ERROR;
                case "ownerPassword" -> ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR;
                case "ownerName" -> ApiValidationErrorCode.NAME_FORMAT_ERROR;
                case "ownerTel" -> ApiValidationErrorCode.TELEPHONE_FORMAT_ERROR;
                case "ownerCompanyName" -> ApiValidationErrorCode.COMPANY_NAME_ERROR;
                default -> ownerErrorCode;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorOwnerMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                ownerErrorCode.getErrorStatus(),
                ownerErrorCode.getErrorDivisionCode(),
                ownerErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(ownerErrorCode.getErrorStatus()).body(response);
    }
}
