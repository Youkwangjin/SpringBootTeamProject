package com.acorn.api.controller.board;

import com.acorn.api.dto.board.response.BoardFileDownloadResDTO;
import com.acorn.api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardFileDownloadController {

    private final BoardService boardService;

    @GetMapping("/api/board/file/download/{boardId}/{boardFileId}")
    public ResponseEntity<byte[]> boardFileDownload(@PathVariable("boardId") Integer boardId, @PathVariable("boardFileId") Integer boardFileId) {
        log.info(" *****************************    Board File  Download START    *****************************");

        BoardFileDownloadResDTO resData = boardService.boardFileDownload(boardId,boardFileId);

        String fileName = UriUtils.encode(resData.getOriginalFileName(), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resData.getFileBytes(), headers, HttpStatus.OK);
    }
}