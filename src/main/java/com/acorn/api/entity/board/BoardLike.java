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
public class BoardLike {

    private Integer boardLikeId;

    private Integer boardLikeBoardId;

    private Integer boardLikeUserId;

    private Integer boardLikeOwnerId;

    private String boardLikeYn;

    private LocalDateTime boardLikeCreated;

    private LocalDateTime boardLikeCanceled;

    private User user;

    private Owner owner;

    private Board board;
}