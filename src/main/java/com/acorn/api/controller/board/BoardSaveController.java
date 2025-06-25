package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.request.BoardSaveReqDTO;
import com.acorn.api.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardSaveController {

    private final BoardService boardService;

    @PostMapping("/api/board/save")
    public ResponseEntity<ApiSuccessResponse<Object>> boardSave(@Valid BoardSaveReqDTO saveData) {
        boardService.boardDataSave(saveData);

        return ApiResponseBuilder.success(ApiSuccessCode.BOARD_SAVE_SUCCESS);
    }
}