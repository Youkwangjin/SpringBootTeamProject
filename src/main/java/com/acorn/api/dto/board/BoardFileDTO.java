package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardFileDTO {

    private Integer boardFileId;

    private Integer boardId;

    private String boardOriginalFileName;

    private String boardStoredFileName;

    private String boardFilePath;

    private String boardFileExtNm;

    private String boardFileSize;
}