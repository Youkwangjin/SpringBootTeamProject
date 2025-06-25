package com.acorn.api.exception.faq;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.admin.AdminFaqManagementController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {AdminFaqManagementController.class})
public class FaqExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  FAQ Data ExceptionHandler Started  ========================  ");

        StringBuilder errorFaqMsg = new StringBuilder();
        ApiValidationErrorCode faqErrorCode = ApiValidationErrorCode.FIELD_BLANK_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorFaqMsg.isEmpty()) {
                errorFaqMsg.append(", ");
            }
            errorFaqMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            faqErrorCode = switch (fieldError.getField()) {
                case "faqId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                case "faqTitle" -> ApiValidationErrorCode.TITLE_LENGTH_ERROR;
                case "faqContents" -> ApiValidationErrorCode.CONTENT_LENGTH_ERROR;
                default -> ApiValidationErrorCode.FIELD_BLANK_ERROR;
            };
        }

        log.info("  ========================  FAQ Data Validation errors  ========================  : {}", errorFaqMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                faqErrorCode.getHttpStatus(),
                faqErrorCode.getErrorDivisionCode(),
                faqErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(faqErrorCode.getHttpStatus()).body(response);
    }
}