package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiHttpErrorCode {

    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "BRE", "잘못된 요청입니다."),

    AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "ACE", "로그인 후 이용 가능합니다."),

    SESSION_EXPIRE(HttpStatus.UNAUTHORIZED, "SEE", "세션이 만료되었습니다. 다시 로그인해주세요."),

    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "FBE", "접근 권한이 없습니다."),

    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "NFE", "요청하신 리소스를 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ISE", "서버 내부 오류가 발생하였습니다. 관리자에게 문의하세요.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiHttpErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}