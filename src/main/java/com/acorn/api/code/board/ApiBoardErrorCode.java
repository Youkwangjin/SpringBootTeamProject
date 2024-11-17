package com.acorn.api.code.board;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiBoardErrorCode {

    BOARD_SAVE_FAILED(HttpStatus.BAD_REQUEST, "BSF", "게시글 등록에 실패했습니다. 필수 입력 값이 누락되었는지 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String boardErrorDivisionCode;
    private final String boardErrorMsg;

    ApiBoardErrorCode(final HttpStatus httpStatus, final String boardErrorDivisionCode, final String boardErrorMsg) {
        this.httpStatus = httpStatus;
        this.boardErrorDivisionCode = boardErrorDivisionCode;
        this.boardErrorMsg = boardErrorMsg;
    }
}