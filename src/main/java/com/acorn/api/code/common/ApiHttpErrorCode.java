package com.acorn.api.code.common;

import lombok.Getter;

@Getter
public enum ApiHttpErrorCode {

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

    // 로그인 실패
    AUTHENTICATION_ERROR(401, "ACE", "로그인 후 이용 가능합니다."),

    // 세션 만료
    SESSION_EXPIRE(401,"SEE", "세션이 만료되었습니다. 다시 로그인해주세요."),

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

    ApiHttpErrorCode(final int errorStatus, final String errorDivisionCode, final String errorMsg) {
        this.errorStatus = errorStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }

}
