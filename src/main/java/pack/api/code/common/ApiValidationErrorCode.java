package pack.api.code.common;

import lombok.Getter;

@Getter
public enum ApiValidationErrorCode {

    // 비밀번호 관련 오류
    PASSWORD_STRENGTH_ERROR(400, "PSE", "제공된 비밀번호가 유효하지 않습니다."),

    // 이메일 관련 오류
    EMAIL_FORMAT_ERROR(400, "EFE", "이메일 형식이 유효하지 않습니다."),

    // 사업자 번호 관련 오류
    BUSINESS_NUMBER_ERROR(400, "BNE", "사업자 번호 형식이 유효하지 않습니다."),

    // 사업자 번호 중복
    BUSINESS_NUMBER_CONFLICT(409, "BNC", "이미 사용 중인 사업자 번호입니다."),

    // 이메일 중복
    EMAIL_DUPLICATED(409, "EDU", "이미 사용 중인 이메일입니다."),

    // 전화번호 중복
    TELEPHONE_DUPLICATED(409, "TPD", "이미 사용 중인 전화번호입니다."),

    // 회사 이름 관련 이름
    COMPANY_NAME_ERROR(400, "CNE", "회사명 형식이 유효하지 않습니다."),

    // 이름 관련 오류
    NAME_FORMAT_ERROR(400, "NFE", "이름 형식이 유효하지 않습니다."),

    // 전화번호 관련 오류
    TELEPHONE_FORMAT_ERROR(400, "TFE", "전화번호 형식이 유효하지 않습니다."),

    // 기타 관련 유류
    VALIDATION_ERROR(400, "VE", "기타 형식이 유효하지 않습니다."),

    // 인증 실패
    UNAUTHORIZED_ERROR(401, "URE", "로그인 이후 이용 가능합니다.");

    // 에러 코드의 '코드 상태'을 반환
    private final int errorStatus;

    // 에러 코드의 '코드간 구분 값'을 반환
    private final String errorDivisionCode;

    // 에러 코드의 '코드 메시지'을 반환
    private final String errorMsg;

    ApiValidationErrorCode(final int errorStatus, final String errorDivisionCode, final String errorMsg) {
        this.errorStatus = errorStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}
