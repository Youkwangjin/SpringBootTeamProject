package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiValidationSuccessCode {

    EMAIL_AVAILABLE(HttpStatus.OK, "EAV", "사용 가능한 이메일입니다."),

    BUSINESS_NUMBER_AVAILABLE(HttpStatus.OK, "BNA", "사용 가능한 사업자 번호입니다."),

    TELEPHONE_AVAILABLE(HttpStatus.OK, "TPA", "사용 가능한 전화번호입니다."),

    COMPANY_NAME_AVAILABLE(HttpStatus.OK, "CNA", "사용 가능한 회사명입니다.");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    ApiValidationSuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}