package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.request.BoardUpdateReqDTO;
import com.acorn.api.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardUpdateController {

    private final BoardService boardService;

    @PatchMapping("/api/board/update/{boardId}")
    public ResponseEntity<ApiSuccessResponse<Object>> boardUpdate (@Valid BoardUpdateReqDTO updateData) {
        boardService.boardDataUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_UPDATE_SUCCESS);
    }
}