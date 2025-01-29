package com.acorn.api.entity.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Board {

    private Integer rowNum;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardPassword;

    private String boardContents;

    private String boardContentsText;

    private Integer boardHits;

    private LocalDateTime boardCreated;

    private LocalDateTime boardUpdated;

    private Integer boardUserId;

    private Integer boardOwnerId;
}