package com.acorn.api.entity.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    private Integer rowNum;

    private Integer boardId;

    private Integer boardUserId;

    private Integer boardOwnerId;

    private String boardTitle;

    private String boardWriter;

    private String boardContents;

    private String boardContentsText;

    private String boardPassword;

    private Integer boardHits;

    private LocalDateTime boardCreated;

    private LocalDateTime boardUpdated;

    private List<BoardFile> boardFilesList;
}