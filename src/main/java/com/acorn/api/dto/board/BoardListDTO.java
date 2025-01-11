package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListDTO {

    private Integer rowNum;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContents;

    private String boardContentsText;

    private Integer boardHits;

    private LocalDateTime boardCreated;

    private LocalDateTime boardUpdated;
}