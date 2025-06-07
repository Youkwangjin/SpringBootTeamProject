package com.acorn.api.dto.board;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeleteDTO {

    @NotNull
    @Positive
    private Integer boardId;

    private Integer boardUserId;

    private Integer boardOwnerId;
}