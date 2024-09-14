package com.acorn.api.com.acorn.api.code.response;


import lombok.Builder;
import lombok.Getter;

/**
 * Global Exception Handler 에서 발생한 에러에 대한 응답 처리를 관리
 */

@Getter
public class ApiErrorResponse {

    private final int errorStatus;
    private final String errorDivisionCode;
    private final String errorMsg;

    @Builder
    public ApiErrorResponse(int errorStatus, String errorDivisionCode, String errorMsg) {
        this.errorStatus = errorStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}
