package pack.api.code.user;

import lombok.Getter;

@Getter
public enum ApiUserErrorCode {

    // 이메일 관련 오류
    EMAIL_FORMAT_ERROR(400, "EFE", "이메일 형식이 유효하지 않습니다."),

    EMAIL_DUPLICATED(409, "EDU", "이미 사용 중인 이메일입니다."),

    // 비밀번호 관련 오류
    PASSWORD_STRENGTH_ERROR(400, "PSE", "비밀번호는 최소 10자 이상이며, 특수 문자를 하나 이상 포함해야 합니다."),

    // 이름 관련 오류
    NAME_FORMAT_ERROR(400, "NFE", "이름 형식이 유효하지 않습니다."),

    // 전화번호 관련 오류
    TELEPHONE_FORMAT_ERROR(400, "TFE", "전화번호 형식이 유효하지 않습니다."),

    TELEPHONE_DUPLICATED(409, "TPD", "이미 사용 중인 전화번호입니다."),

    // 기타 관련 유류
    VALIDATION_ERROR(400, "VE", "기타 형식이 유효하지 않습니다."),

    // 인증 실패 (잘못된 이메일 또는 비밀번호)
    USER_AUTHENTICATION_FAILED(401, "AFD", "이메일 또는 비밀번호가 올바르지 않습니다.");

    private final int userErrorStatus;

    private final String userErrorDivisionCode;

    private final String userErrorMsg;

    ApiUserErrorCode(int userErrorStatus, String userErrorDivisionCode, String userErrorMsg) {
        this.userErrorStatus = userErrorStatus;
        this.userErrorDivisionCode = userErrorDivisionCode;
        this.userErrorMsg = userErrorMsg;
    }
}
