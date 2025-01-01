package com.acorn.api.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Board {

    private int rowNum;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardPassword;

    private String boardContents;

    private String boardContentsText;

    private String boardHits;

    private String boardCreated;

    private String boardUpdated;
}