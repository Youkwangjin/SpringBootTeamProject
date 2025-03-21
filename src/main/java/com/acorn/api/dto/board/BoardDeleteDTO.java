package com.acorn.api.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeleteDTO {

    private Integer boardId;

    private Integer boardUserId;

    private Integer boardOwnerId;
}