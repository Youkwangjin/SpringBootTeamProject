package com.acorn.api.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

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

    private Integer boardUserId;

    private Integer boardOwnerId;

    private String boardHits;

    private Timestamp boardCreated;

    private Timestamp boardUpdated;
}