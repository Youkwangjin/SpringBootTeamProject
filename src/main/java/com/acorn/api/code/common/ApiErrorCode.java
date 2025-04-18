package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {
    USER_FOUND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,               "UFE", "존재하지 않는 사용자 입니다."),
    BOARD_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,                "BNF", "게시글이 존재하지 않습니다."),
    CONTAINER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,            "CNF", "창고가 존재하지 않습니다."),
    CONTAINER_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,          "CUF", "창고 정보 수정에 실패하였습니다."),
    CONTAINER_APPROVAL_NOT_PENDING(HttpStatus.INTERNAL_SERVER_ERROR, "CAP", "승인 대기 상태에서만 수정 및 삭제가 가능합니다."),
    CONTAINER_STATUS_NOT_PENDING(HttpStatus.INTERNAL_SERVER_ERROR,   "CSP", "사용 대기 상태에서만 수정 및 삭제가 가능합니다."),
    FILE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,                 "FNF", "파일 데이터가 존재하지 않습니다."),
    FILE_PATH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,                "FPE", "파일 업로드 디렉토리가 존재하지 않습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,              "FUE", "파일 업로드에 실패하였습니다."),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,              "FDE", "파일 삭제 중 문제가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}