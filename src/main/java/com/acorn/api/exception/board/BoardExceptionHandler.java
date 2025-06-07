package com.acorn.api.exception.board;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.board.BoardSaveController;
import com.acorn.api.controller.board.BoardUpdateController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {BoardSaveController.class, BoardUpdateController.class})
public class BoardExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  Board Data ExceptionHandler Started  ========================  ");

        StringBuilder errorBoardMsg = new StringBuilder();
        ApiValidationErrorCode boardErrorCode = ApiValidationErrorCode.FIELD_BLANK_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorBoardMsg.isEmpty()) {
                errorBoardMsg.append(", ");
            }
            errorBoardMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            boardErrorCode = switch (fieldError.getField()) {
                case "boardId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                case "boardTitle" -> ApiValidationErrorCode.TITLE_LENGTH_ERROR;
                case "boardPassword" -> ApiValidationErrorCode.PASSWORD_LENGTH_ERROR;
                default -> ApiValidationErrorCode.FIELD_BLANK_ERROR;
            };
        }

        log.info("  ========================  Board Data Validation errors  ========================  : {}", errorBoardMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                boardErrorCode.getHttpStatus(),
                boardErrorCode.getErrorDivisionCode(),
                boardErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(boardErrorCode.getHttpStatus()).body(response);
    }
}