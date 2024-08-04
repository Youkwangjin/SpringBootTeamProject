package pack.api.code.common;

import lombok.Getter;

@Getter
public enum ApiErrorCode {

    /*
      *************************************** Global Error CodeList ***************************************
      HTTP Status Code
      400 : Bad Request
      401 : Unauthorized
      403 : Forbidden
      404 : Not Found
      500 : Internal Server Error
      * ***************************************************************************************************
     */

    // 잘못된 요청
    BAD_REQUEST_ERROR(400, "BRE", "잘못된 요청입니다."),

    // 인증 실패
    UNAUTHORIZED_ERROR(401, "URE", "로그인 이후 이용 가능합니다."),

    // 권한 없음
    FORBIDDEN_ERROR(403, "FBE", "접근 권한이 없습니다."),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "NFE", "요청하신 리소스를 찾을 수 없습니다."),

    // 서버 오류
    INTERNAL_SERVER_ERROR(500, "ISE", "서버 내부 오류가 발생하였습니다. 관리자에게 문의하세요.");

    /**
     * ******************************* Error Code Constructor ***************************************
     */

    // 에러 코드의 '코드 상태'을 반환
    private final int errorStatus;

    // 에러 코드의 '코드간 구분 값'을 반환
    private final String errorDivisionCode;

    // 에러 코드의 '코드 메시지'을 반환
    private final String errorMsg;

    ApiErrorCode(final int errorStatus, final String errorDivisionCode, final String errorMsg) {
        this.errorStatus = errorStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }

}
