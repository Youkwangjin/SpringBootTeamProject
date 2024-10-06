package com.acorn.api.controller.board;


import com.acorn.api.code.board.ApiBoardSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.board.BoardSaveDTO;
import com.acorn.api.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardRegisterController {

    @Value("${file.upload.path}")
    private String imageUploadPath;

    @Value("${response.img.url}")
    private String imageUrl;

    private static final Logger log = LoggerFactory.getLogger(BoardRegisterController.class);
    private final BoardService boardService;

    @PostMapping("/api/editor/image/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, String> responseData = boardService.saveImageFile(file, imageUploadPath, imageUrl);
            return ResponseEntity.ok(responseData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @PostMapping("/api/board/save")
    public ResponseEntity<ApiSuccessResponse<Object>> boardSave(@Valid BoardSaveDTO boardSaveDTO) {

        log.info(" *****************************    Board Register START    *****************************");

        boardService.boardDataSave(boardSaveDTO);

        ApiSuccessResponse<Object> boardSaveResponse = ApiSuccessResponse.builder()
                .resultCode(ApiBoardSuccessCode.BOARD_SAVE_SUCCESS.getBoardSuccessStatus())
                .resultMsg(ApiBoardSuccessCode.BOARD_SAVE_SUCCESS.getBoardSuccessMsg())
                .build();
        return ResponseEntity.status(ApiBoardSuccessCode.BOARD_SAVE_SUCCESS.getBoardSuccessStatus()).body(boardSaveResponse);
    }
}
