package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {
    UNAUTHORIZED_USER_ERROR(HttpStatus.UNAUTHORIZED, "UNU", "존재하지 않는 사용자 입니다."),
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST,          "BNF", "게시글이 존재하지 않습니다.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}