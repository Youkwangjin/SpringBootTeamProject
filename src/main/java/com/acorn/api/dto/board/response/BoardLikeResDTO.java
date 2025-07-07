package com.acorn.api.dto.board.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardLikeResDTO {

    private Integer boardId;

    private Integer boardLikeCount;

    private String boardLikeYn;
}