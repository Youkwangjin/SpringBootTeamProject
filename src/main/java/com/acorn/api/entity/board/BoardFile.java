package com.acorn.api.entity.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BoardFile {
    private Integer boardFileId;

    private Integer boardId;

    private String boardOriginalFileName;

    private String boardStoredFileName;

    private String boardFilePath;

    private String boardFileExtNm;

    private String boardFileSize;

    private LocalDateTime boardFileCreated;

    private LocalDateTime boardFileUpdated;
}