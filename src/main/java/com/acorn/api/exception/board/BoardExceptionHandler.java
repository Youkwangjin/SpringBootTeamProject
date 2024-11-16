package com.acorn.api.exception.board;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.board.BoardSaveController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = BoardSaveController.class)
public class BoardExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BoardExceptionHandler.class);

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

            boardErrorCode = boardFieldErrorCode(fieldError);

        }

        log.info("  ========================  Board Data Validation errors  ========================  : {}", errorBoardMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                boardErrorCode.getErrorStatus(),
                boardErrorCode.getErrorDivisionCode(),
                boardErrorCode.getErrorMsg()
        );
        return ResponseEntity.status(boardErrorCode.getErrorStatus()).body(response);

    }

    private ApiValidationErrorCode boardFieldErrorCode(FieldError fieldError) {

        String defaultMessage = fieldError.getDefaultMessage();

        if (!StringUtils.hasText(defaultMessage) || "must not be blank".equals(defaultMessage)) {
            return ApiValidationErrorCode.FIELD_BLANK_ERROR;
        }

        return switch (fieldError.getField()) {
            case "boardTitle" -> ApiValidationErrorCode.BOARD_TITLE_LENGTH_ERROR;
            case "boardPassword" -> {
                if (defaultMessage.contains("size must be between")) {
                    yield ApiValidationErrorCode.PASSWORD_LENGTH_ERROR;
                } else {
                    yield ApiValidationErrorCode.FIELD_BLANK_ERROR;
                }
            }

            default -> ApiValidationErrorCode.FIELD_BLANK_ERROR;
        };
    }
}
