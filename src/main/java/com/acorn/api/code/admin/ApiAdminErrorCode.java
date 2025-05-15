package com.acorn.api.code.admin;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiAdminErrorCode {

    ADMIN_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AFD", "이메일 또는 비밀번호가 일치하지 않습니다. 보안을 위해 일정 횟수 이상 실패 시 계정이 잠금 처리됩니다."),
    ADMIN_FOUND_ERROR(HttpStatus.FORBIDDEN, "AFE", "존재하지 않는 관리자 입니다."),
    ADMIN_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "APE", "관리자 비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;

    private final String adminErrorDivisionCode;

    private final String adminErrorMsg;

    ApiAdminErrorCode(final HttpStatus httpStatus, final String adminErrorDivisionCode, final String adminErrorMsg) {
        this.httpStatus = httpStatus;
        this.adminErrorDivisionCode = adminErrorDivisionCode;
        this.adminErrorMsg = adminErrorMsg;
    }
}