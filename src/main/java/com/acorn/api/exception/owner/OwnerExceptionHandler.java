package com.acorn.api.exception.owner;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.owner.OwnerDeleteController;
import com.acorn.api.controller.owner.OwnerRegisterController;
import com.acorn.api.controller.owner.OwnerUpdateController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {OwnerRegisterController.class, OwnerUpdateController.class, OwnerDeleteController.class})
public class OwnerExceptionHandler {

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
                default -> ApiValidationErrorCode.VALIDATION_ERROR;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorOwnerMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                ownerErrorCode.getHttpStatus(),
                ownerErrorCode.getErrorDivisionCode(),
                ownerErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(ownerErrorCode.getHttpStatus()).body(response);
    }
}