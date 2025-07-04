package com.acorn.api.dto.board.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BoardDetailResDTO {

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContents;

    private String boardContentsText;

    private String boardLikeYn;

    private Integer boardHits;

    private Integer boardLikeCount;

    private LocalDateTime boardCreated;

    private Integer boardUserId;

    private Integer boardOwnerId;

    private boolean isAuthor;

    private List<BoardFileResDTO> boardFiles;
}