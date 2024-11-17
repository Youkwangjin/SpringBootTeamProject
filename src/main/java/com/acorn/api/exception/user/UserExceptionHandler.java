package com.acorn.api.exception.user;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.user.UserRegisterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses  = UserRegisterController.class)
public class UserExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

         log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorUserMsg = new StringBuilder();

        ApiValidationErrorCode userErrorCode = ApiValidationErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorUserMsg.isEmpty()) {
                errorUserMsg.append(", ");
            }
            errorUserMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            userErrorCode = switch (fieldError.getField()) {
                case "userEmail" -> ApiValidationErrorCode.EMAIL_FORMAT_ERROR;
                case "userPassword" -> ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR;
                case "userDisplayName" -> ApiValidationErrorCode.NAME_FORMAT_ERROR;
                case "userTel" -> ApiValidationErrorCode.TELEPHONE_FORMAT_ERROR;
                default -> userErrorCode;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorUserMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                userErrorCode.getHttpStatus(),
                userErrorCode.getErrorDivisionCode(),
                userErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(userErrorCode.getHttpStatus()).body(response);
    }
}