package com.acorn.api.code.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiUserErrorCode {

    USER_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AFD", "이메일 또는 비밀번호가 올바르지 않습니다.");

    private final HttpStatus httpStatus;

    private final String userErrorDivisionCode;

    private final String userErrorMsg;

    ApiUserErrorCode(final HttpStatus httpStatus, final String userErrorDivisionCode, final String userErrorMsg) {
        this.httpStatus = httpStatus;
        this.userErrorDivisionCode = userErrorDivisionCode;
        this.userErrorMsg = userErrorMsg;
    }
}