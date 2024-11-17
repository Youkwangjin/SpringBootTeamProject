package com.acorn.api.code.board;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiBoardSuccessCode {

    BOARD_SAVE_SUCCESS(HttpStatus.OK, "BSS", "게시글이 성공적으로 등록되었습니다.");

    private final HttpStatus httpStatus;

    private final String boardSuccessDivisionCode;

    private final String boardSuccessMsg;

    ApiBoardSuccessCode(final HttpStatus httpStatus, final String boardSuccessDivisionCode, final String boardSuccessMsg) {
        this.httpStatus = httpStatus;
        this.boardSuccessDivisionCode = boardSuccessDivisionCode;
        this.boardSuccessMsg = boardSuccessMsg;
    }
}