package com.acorn.api.controller.board;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.BoardSaveDTO;
import com.acorn.api.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardSaveController {

    private static final Logger log = LoggerFactory.getLogger(BoardSaveController.class);
    private final BoardService boardService;

    @PostMapping("/api/board/save")
    public ResponseEntity<ApiSuccessResponse<Object>> boardSave(@Valid BoardSaveDTO boardSaveDTO) {
        log.info(" *****************************    Board Save START    *****************************");

        boardService.boardDataSave(boardSaveDTO);

        return ApiResponseBuilder.success(ApiHttpSuccessCode.BOARD_SAVE_SUCCESS);
    }
}