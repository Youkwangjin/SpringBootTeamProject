package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.request.BoardLikeReqDTO;
import com.acorn.api.dto.board.response.BoardLikeResDTO;
import com.acorn.api.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardService boardService;

    @PostMapping("/api/board/like/{boardId}")
    public ResponseEntity<ApiSuccessResponse<Object>> likeBoard(@Valid @RequestBody BoardLikeReqDTO requestData) {
        BoardLikeResDTO resData = boardService.boardLike(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_LIKE_CHANGE, resData);
    }
}