package pack.api.code.owner;

import lombok.Getter;

@Getter
public enum ApiOwnerSuccessCode {


    BUSINESS_NUMBER_AVAILABLE(200, "BNA", "사용 가능한 사업자 번호입니다.");


    // user API 성공 코드의 '코드 상태'를 반환
    private final int ownerApiStatus;

    //  user API 성공 코드의 '코드 값'을 반환
    private final String ownerApiCode;

    //  user API 성공 코드의 '코드 메시지'를 반환
    private final String ownerApiMessage;

    ApiOwnerSuccessCode(final int ownerApiStatus, final String ownerApiCode, final String ownerApiMessage) {
        this.ownerApiStatus = ownerApiStatus;
        this.ownerApiCode = ownerApiCode;
        this.ownerApiMessage = ownerApiMessage;
    }
}
