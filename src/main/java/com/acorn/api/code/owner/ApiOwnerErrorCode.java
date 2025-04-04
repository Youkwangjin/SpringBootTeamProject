package com.acorn.api.code.owner;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiOwnerErrorCode {

    OWNER_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED,   "OFD", "사업자 번호 또는 비밀번호가 올바르지 않습니다."),
    OWNER_DELETION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ODE", "활성화된 창고가 존재하여 회원탈퇴를 진행할 수 없습니다.");

    private final HttpStatus httpStatus;

    private final String ownerErrorDivisionCode;

    private final String ownerErrorMsg;

    ApiOwnerErrorCode(final HttpStatus httpStatus, final String ownerErrorDivisionCode, final String ownerErrorMsg) {
        this.httpStatus = httpStatus;
        this.ownerErrorDivisionCode = ownerErrorDivisionCode;
        this.ownerErrorMsg = ownerErrorMsg;
    }
}