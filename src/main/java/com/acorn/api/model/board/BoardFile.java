package com.acorn.api.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BoardFile {

    private int boardFileId;

    private int boardId;

    private String boardOriginalFileName;

    private String boardStoredFileName;

    private String boardFileSize;
}
