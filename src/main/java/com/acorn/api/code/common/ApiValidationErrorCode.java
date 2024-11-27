package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiValidationErrorCode {

    PASSWORD_STRENGTH_ERROR(HttpStatus.BAD_REQUEST, "PSE", "제공된 비밀번호가 유효하지 않습니다."),

    EMAIL_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "EFE", "이메일 형식이 유효하지 않습니다."),

    BUSINESS_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "BNE", "사업자 번호 형식이 유효하지 않습니다."),

    BUSINESS_NUMBER_CONFLICT(HttpStatus.CONFLICT, "BNC", "이미 사용 중인 사업자 번호입니다."),

    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "EDU", "이미 사용 중인 이메일입니다."),

    TELEPHONE_DUPLICATED(HttpStatus.CONFLICT, "TPD", "이미 사용 중인 전화번호입니다."),

    COMPANY_NAME_ERROR(HttpStatus.CONFLICT, "CNE", "이미 사용 중인 회사명입니다."),

    NAME_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "NFE", "이름 형식이 유효하지 않습니다."),

    TELEPHONE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "TFE", "전화번호 형식이 유효하지 않습니다."),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VE", "기타 형식이 유효하지 않습니다."),

    BOARD_TITLE_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "BTL", "게시글 제목은 50글자 미만이어야 합니다."),

    FIELD_BLANK_ERROR(HttpStatus.BAD_REQUEST, "FBE", "필수 입력 항목이 비어 있습니다."),

    PASSWORD_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "PLE", "비밀번호는 6글자 이상이어야 합니다."),

    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "URE", "로그인 이후 이용 가능합니다.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiValidationErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}