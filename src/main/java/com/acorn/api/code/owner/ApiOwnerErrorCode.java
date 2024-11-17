package com.acorn.api.code.owner;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiOwnerErrorCode {

    OWNER_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "OFD", "사업자 번호 또는 비밀번호가 올바르지 않습니다.");

    private final HttpStatus httpStatus;

    private final String ownerErrorDivisionCode;

    private final String ownerErrorMsg;

    ApiOwnerErrorCode(final HttpStatus httpStatus, final String ownerErrorDivisionCode, final String ownerErrorMsg) {
        this.httpStatus = httpStatus;
        this.ownerErrorDivisionCode = ownerErrorDivisionCode;
        this.ownerErrorMsg = ownerErrorMsg;
    }
}