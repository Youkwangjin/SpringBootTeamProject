package com.acorn.api.code.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiSuccessResponse<T> {

    private final HttpStatus httpStatus;

    private final T result;

    private final String resultMsg;

    @Builder
    public ApiSuccessResponse(final HttpStatus httpStatus, final T result, final String resultMsg) {
        this.httpStatus = httpStatus;
        this.result = result;
        this.resultMsg = resultMsg;
    }
}