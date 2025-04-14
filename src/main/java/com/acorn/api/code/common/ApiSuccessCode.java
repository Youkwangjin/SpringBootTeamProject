package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiSuccessCode {
    REGISTER_SUCCESS(HttpStatus.CREATED,      "RIS", "회원가입이 정상적으로 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,              "LIS", "로그인이 정상적으로 완료되었습니다."),
    BOARD_SAVE_SUCCESS(HttpStatus.OK,         "BSS", "게시글이 성공적으로 등록되었습니다."),
    BOARD_UPDATE_SUCCESS(HttpStatus.OK,       "BUS", "게시글이 성공적으로 수정되었습니다."),
    BOARD_DELETE_SUCCESS(HttpStatus.OK,       "BDS", "게시글이 성공적으로 삭제되었습니다."),
    CONTAINER_REGISTER_SUCCESS(HttpStatus.OK, "CRS", "창고정보가 성공적으로 등록되었습니다."),
    CONTAINER_UPDATE_SUCCESS(HttpStatus.OK,   "CUS", "창고정보가 성공적으로 수정되었습니다."),
    CONTAINER_DELETE_SUCCESS(HttpStatus.OK,   "CDS", "창고정보가 성공적으로 삭제되었습니다."),
    CONTAINER_REVIEW_SUCCESS(HttpStatus.OK,   "CRS", "해당 창고가 검토 상태로 변경되었습니다."),
    INFO_UPDATE_SUCCESS(HttpStatus.OK,        "IUS", "회원정보가 성공적으로 수정되었습니다."),
    INFO_DELETE_SUCCESS(HttpStatus.OK,        "IDS", "그동안 이용해 주셔서 감사합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ApiSuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}