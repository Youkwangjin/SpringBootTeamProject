package com.acorn.api.code.board;

import lombok.Getter;

@Getter
public enum ApiBoardSuccessCode {

    BOARD_SAVE_SUCCESS(200, "BSS", "게시글이 성공적으로 등록되었습니다.");

    private final int boardSuccessStatus;

    private final String boardSuccessDivisionCode;

    private final String boardSuccessMsg;

    ApiBoardSuccessCode(int boardSuccessStatus, String boardSuccessDivisionCode, String boardSuccessMsg) {
        this.boardSuccessStatus = boardSuccessStatus;
        this.boardSuccessDivisionCode = boardSuccessDivisionCode;
        this.boardSuccessMsg = boardSuccessMsg;
    }
}
