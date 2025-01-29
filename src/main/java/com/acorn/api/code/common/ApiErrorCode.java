package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {

    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "BNF", "게시글이 존재하지 않습니다."),

    BOARD_SAVE_FAILED(HttpStatus.BAD_REQUEST, "BSF", "게시글 등록에 실패했습니다. 필수 입력 값이 누락되었는지 확인해주세요.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}