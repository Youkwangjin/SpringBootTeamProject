package pack.api.code.common;


import lombok.Getter;

@Getter
public enum ApiSuccessCode {

    /**
     * ******************************* Global Success CodeList ***************************************
     */

    // 회원 가입 성공 코드 (HTTP Response: 201 Created)
    REGISTER_INSERT_SUCCESS(201, "RIS", "회원가입이 정상적으로 완료되었습니다."),

    // 로그인 성공 코드 (HTTP Response: 200 OK)
    LOGIN_SUCCESS(200, "LIS", "로그인이 정상적으로 완료되었습니다."),

    // 수정 성공 코드 (HTTP Response: 204 OK)
    INFO_UPDATE_SUCCESS(204, "IUS", "회원정보가 성공적으로 수정되었습니다. 다시 로그인 해주세요."),

    // 삭제 성공 코드 (HTTP Response: 200 OK)
    INFO_DELETE_SUCCESS(200, "IDS", "그동안 이용해 주셔서 감사합니다.");


    // 성공 코드의 '코드 상태'를 반환
    private final int status;

    // 성공 코드의 '코드 값'을 반환
    private final String code;

    // 성공 코드의 '코드 메시지'를 반환
    private final String message;

    ApiSuccessCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
