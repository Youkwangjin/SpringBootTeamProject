package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.request.BoardDeleteReqDTO;
import com.acorn.api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardDeleteController {

    private final BoardService boardService;

    @PostMapping("/api/board/delete/{boardId}")
    public ResponseEntity<ApiSuccessResponse<Object>> boardDelete(@RequestBody BoardDeleteReqDTO deleteData) {
        boardService.boardDataDelete(deleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_DELETE_SUCCESS);
    }
}