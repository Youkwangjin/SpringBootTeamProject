package com.acorn.api.dto.board.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BoardCommentResDTO {

    private Integer boardCommentId;

    private Integer boardCommentBoardId;

    private Integer boardCommentUserId;

    private Integer boardCommentOwnerId;

    private Integer boardCommentParentCommentId;

    private String boardCommentWriter;

    private String boardCommentContents;

    private String boardCommentYn;

    private LocalDateTime boardCommentCreated;

    private LocalDateTime boardCommentUpdated;

    private List<BoardCommentResDTO> childComments;
}