package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BoardDetailDTO {

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardPassword;

    private String boardContents;

    private Integer boardHits;

    private LocalDateTime boardCreated;

    private LocalDateTime boardUpdated;

    private Integer boardUserId;

    private Integer boardOwnerId;

    private List<BoardFileDTO> boardFiles;
}