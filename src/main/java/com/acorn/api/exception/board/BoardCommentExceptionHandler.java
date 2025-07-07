package com.acorn.api.exception.board;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.board.BoardCommentSaveController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {BoardCommentSaveController.class})
public class BoardCommentExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  BoardComment Data ExceptionHandler Started  ========================  ");

        StringBuilder errorBoardCommentMsg = new StringBuilder();
        ApiValidationErrorCode boardCommentErrorCode = ApiValidationErrorCode.FIELD_BLANK_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorBoardCommentMsg.isEmpty()) {
                errorBoardCommentMsg.append(", ");
            }
            errorBoardCommentMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            boardCommentErrorCode = switch (fieldError.getField()) {
                case "boardId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                case "boardCommentContents" -> ApiValidationErrorCode.CONTENT_LENGTH_ERROR;
                case "boardCommentYn" -> ApiValidationErrorCode.VALIDATION_ERROR;
                default -> ApiValidationErrorCode.FIELD_BLANK_ERROR;
            };
        }

        log.info("  ========================  BoardComment Data Validation errors  ========================  : {}", errorBoardCommentMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                boardCommentErrorCode.getHttpStatus(),
                boardCommentErrorCode.getErrorDivisionCode(),
                boardCommentErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(boardCommentErrorCode.getHttpStatus()).body(response);
    }
}