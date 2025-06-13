package com.acorn.api.dto.board.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BoardDeleteReqDTO {

    @NotNull
    @Positive
    private Integer boardId;

    private Integer boardUserId;

    private Integer boardOwnerId;
}