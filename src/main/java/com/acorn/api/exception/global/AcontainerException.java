package com.acorn.api.exception.global;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.common.ApiValidationErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AcontainerException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    public AcontainerException(ApiValidationErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorDivisionCode = errorCode.getErrorDivisionCode();
        this.errorMsg = errorCode.getErrorMsg();
    }

    public AcontainerException(ApiErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorDivisionCode = errorCode.getErrorDivisionCode();
        this.errorMsg = errorCode.getErrorMsg();
    }

    public AcontainerException(ApiHttpErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorDivisionCode = errorCode.getErrorDivisionCode();
        this.errorMsg = errorCode.getErrorMsg();
    }
}