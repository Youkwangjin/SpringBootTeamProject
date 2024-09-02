package pack.api.code.common;

import lombok.Getter;

@Getter
public enum ApiValidationSuccessCode {

    // 이메일
    EMAIL_AVAILABLE(200, "EAV", "사용 가능한 이메일입니다."),

    // 사업자 번호
    BUSINESS_NUMBER_AVAILABLE(200, "BNA", "사용 가능한 사업자 번호입니다."),

    // 전화번호
    TELEPHONE_AVAILABLE(200, "TPA", "사용 가능한 전화번호 입니다."),

    // 회사명
    COMPANY_NAME_AVAILABLE(200, "CNA", "사용 가능한 회사명 입니다.");

    // 성공 코드의 '코드 상태'를 반환
    private final int status;

    // 성공 코드의 '코드 값'을 반환
    private final String code;

    // 성공 코드의 '코드 메시지'를 반환
    private final String message;

    ApiValidationSuccessCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
