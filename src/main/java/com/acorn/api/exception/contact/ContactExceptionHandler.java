package com.acorn.api.exception.contact;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.admin.AdminContactManagementController;
import com.acorn.api.controller.contact.ContactSaveController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {ContactSaveController.class, AdminContactManagementController.class})
public class ContactExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  Contact Data ExceptionHandler Started  ========================  ");

        StringBuilder errorContactMsg = new StringBuilder();
        ApiValidationErrorCode contactErrorCode = ApiValidationErrorCode.FIELD_BLANK_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorContactMsg.isEmpty()) {
                errorContactMsg.append(", ");
            }
            errorContactMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            contactErrorCode = switch (fieldError.getField()) {
                case "contactId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                case "contactTitle" -> ApiValidationErrorCode.TITLE_LENGTH_ERROR;
                case "contactContents" -> ApiValidationErrorCode.CONTENT_LENGTH_ERROR;
                default -> ApiValidationErrorCode.FIELD_BLANK_ERROR;
            };
        }

        log.info("  ========================  Contact Data Validation errors  ========================  : {}", errorContactMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                contactErrorCode.getHttpStatus(),
                contactErrorCode.getErrorDivisionCode(),
                contactErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(contactErrorCode.getHttpStatus()).body(response);
    }
}