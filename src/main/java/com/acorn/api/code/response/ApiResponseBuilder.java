package com.acorn.api.code.response;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.common.ApiValidationSuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseBuilder {

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(HttpStatus httpStatus, T result, String message) {
        ApiSuccessResponse<T> response = ApiSuccessResponse.of(httpStatus, result, message);
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(ApiHttpSuccessCode successCode, T result) {
        return success(successCode.getHttpStatus(), result, successCode.getMessage());
    }

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(ApiHttpSuccessCode successCode) {
        return success(successCode.getHttpStatus(), null, successCode.getMessage());
    }

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(ApiValidationSuccessCode successCode, T result) {
        return success(successCode.getHttpStatus(), result, successCode.getMessage());
    }

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(ApiValidationSuccessCode successCode) {
        return success(successCode.getHttpStatus(), null, successCode.getMessage());
    }
}