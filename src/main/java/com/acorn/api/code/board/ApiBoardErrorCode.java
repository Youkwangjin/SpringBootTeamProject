package com.acorn.api.code.board;

import lombok.Getter;

@Getter
public enum ApiBoardErrorCode {

    BOARD_SAVE_FAILED(400, "BSF", "게시글 등록에 실패했습니다. 필수 입력 값이 누락되었는지 확인해주세요.");

    private final int boardErrorStatus;

    private final String boardErrorDivisionCode;

    private final String boardErrorMsg;

    ApiBoardErrorCode(int boardErrorStatus, String boardErrorDivisionCode, String boardErrorMsg) {
        this.boardErrorStatus = boardErrorStatus;
        this.boardErrorDivisionCode = boardErrorDivisionCode;
        this.boardErrorMsg = boardErrorMsg;
    }
}
