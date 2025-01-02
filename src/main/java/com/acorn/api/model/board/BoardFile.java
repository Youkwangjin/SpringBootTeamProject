package com.acorn.api.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}