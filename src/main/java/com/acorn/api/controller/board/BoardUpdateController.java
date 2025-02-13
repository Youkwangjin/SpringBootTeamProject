package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.BoardUpdateDTO;
import com.acorn.api.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardUpdateController {

    private final BoardService boardService;

    @PatchMapping("/api/board/update/{boardId}")
    public ResponseEntity<ApiSuccessResponse<Object>> boardUpdate (@Valid BoardUpdateDTO updateData) {
        log.info(" *****************************    Board Update START    *****************************");

        boardService.boardDataUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_UPDATE_SUCCESS);
    }
}