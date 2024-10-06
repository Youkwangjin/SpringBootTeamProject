package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponseDTO {

    private int rowNum;

    private int boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContents;

    private String boardContentsText;

    private String boardHits;

    private String boardCreated;

    private String boardUpdated;
}
