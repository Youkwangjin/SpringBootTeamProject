package pack.api.code.owner;

import lombok.Getter;

@Getter
public enum ApiOwnerErrorCode {

    OWNER_AUTHENTICATION_FAILED(401, "OFD", "사업자 번호 또는 비밀번호가 올바르지 않습니다.");

    private final int ownerErrorStatus;

    private final String ownerErrorDivisionCode;

    private final String ownerErrorMsg;

    ApiOwnerErrorCode(int ownerErrorStatus, String ownerErrorDivisionCode, String ownerErrorMsg) {
        this.ownerErrorStatus = ownerErrorStatus;
        this.ownerErrorDivisionCode = ownerErrorDivisionCode;
        this.ownerErrorMsg = ownerErrorMsg;
    }
}
