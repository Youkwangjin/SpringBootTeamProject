package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class BoardListDTO {

    private int pageNo;

    private String searchName;

    private String searchType;

    private int rowNum;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContents;

    private String boardContentsText;

    private String boardHits;

    private Timestamp boardCreated;

    private Timestamp boardUpdated;
}