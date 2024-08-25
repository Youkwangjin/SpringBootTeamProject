package pack.api.code.user;

import lombok.Getter;

@Getter
public enum ApiUserErrorCode {

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
