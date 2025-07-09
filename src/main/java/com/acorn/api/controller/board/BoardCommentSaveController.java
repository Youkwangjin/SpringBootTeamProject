package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.request.BoardCommentReplySaveReqDTO;
import com.acorn.api.dto.board.request.BoardCommentSaveReqDTO;
import com.acorn.api.service.board.BoardCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardCommentSaveController {

    private final BoardCommentService boardCommentService;

    @PostMapping("/api/board/comment/save/{boardId}")
    public ResponseEntity<ApiSuccessResponse<Object>> boardCommentSave(@Valid @RequestBody BoardCommentSaveReqDTO reqestData) {
        boardCommentService.boardCommentSave(reqestData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_SAVE_SUCCESS);
    }

    @PostMapping("/api/board/comment/reply/{boardId}")
    public ResponseEntity<ApiSuccessResponse<Object>> boardCommentReply(@Valid @RequestBody BoardCommentReplySaveReqDTO reqestData) {
        boardCommentService.boardCommentReplySave(reqestData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_SAVE_SUCCESS);
    }
}