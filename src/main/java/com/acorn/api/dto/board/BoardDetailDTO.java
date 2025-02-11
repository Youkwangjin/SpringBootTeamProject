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

    private String boardContentsText;

    private Integer boardHits;

    private LocalDateTime boardCreated;

    private Integer boardUserId;

    private Integer boardOwnerId;

    private List<BoardFileDTO> boardFiles;
}