package com.acorn.api.code.response;


import lombok.Builder;
import lombok.Getter;

/**
 * [공통] API Response 결과의 반환 값을 관리
 */
@Getter
public class ApiSuccessResponse<T> {

    // API 응답 결과 Response
    private final T result;

    // API 응답 코드 Response
    private final int resultCode;

    // API 응답 코드 Message
    private final String resultMsg;

    @Builder
    public ApiSuccessResponse(final T result, final int resultCode, final String resultMsg) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

}
