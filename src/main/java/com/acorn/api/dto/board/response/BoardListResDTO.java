package com.acorn.api.dto.board.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListResDTO {

    private Integer rowNum;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContentsText;

    private Integer boardHits;

    private Integer boardLikeCount;

    private LocalDateTime boardCreated;
}