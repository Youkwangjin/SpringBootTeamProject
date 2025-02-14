package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDeleteDTO {

    private Integer boardId;

    private Integer boardUserId;

    private Integer boardOwnerId;
}