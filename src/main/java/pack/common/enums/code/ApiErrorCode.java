package pack.common.enums.code;

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
    BAD_REQUEST_ERROR(400, "BRE", "Bad Request Error"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "NFE", "Not Found Exception"),

    // 유효성 검사 실패
    VALIDATION_ERROR(400, "VE", "Validation Error");

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
