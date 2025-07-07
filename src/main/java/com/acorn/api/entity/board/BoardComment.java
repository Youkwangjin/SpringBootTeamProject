package com.acorn.api.entity.board;

import com.acorn.api.entity.owner.Owner;
import com.acorn.api.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardComment {

    private Integer boardCommentId;

    private Integer boardCommentBoardId;

    private Integer boardCommentUserId;

    private Integer boardCommentOwnerId;

    private Integer boardCommentParentCommentId;

    private String boardCommentContents;

    private String boardCommentYn;

    private LocalDateTime boardCommentCreated;

    private LocalDateTime boardCommentUpdated;

    private User user;

    private Owner owner;
}