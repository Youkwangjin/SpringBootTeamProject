package com.acorn.api.code.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    @Builder
    public ApiErrorResponse(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}