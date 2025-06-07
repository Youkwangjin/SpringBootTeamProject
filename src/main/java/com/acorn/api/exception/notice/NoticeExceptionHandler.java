package com.acorn.api.exception.notice;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.notice.NoticeSaveController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {NoticeSaveController.class})
public class NoticeExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  Notice Data ExceptionHandler Started  ========================  ");

        StringBuilder errorNoticeMsg = new StringBuilder();
        ApiValidationErrorCode noticeErrorCode = ApiValidationErrorCode.FIELD_BLANK_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorNoticeMsg.isEmpty()) {
                errorNoticeMsg.append(", ");
            }
            errorNoticeMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            noticeErrorCode = switch (fieldError.getField()) {
                case "noticeId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                case "noticeTitle" -> ApiValidationErrorCode.TITLE_LENGTH_ERROR;
                case "noticeContents" -> ApiValidationErrorCode.CONTENT_LENGTH_ERROR;
                default -> ApiValidationErrorCode.FIELD_BLANK_ERROR;
            };
        }

        log.info("  ========================  Notice Data Validation errors  ========================  : {}", errorNoticeMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                noticeErrorCode.getHttpStatus(),
                noticeErrorCode.getErrorDivisionCode(),
                noticeErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(noticeErrorCode.getHttpStatus()).body(response);
    }
}