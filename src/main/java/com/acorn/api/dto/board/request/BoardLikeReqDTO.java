package com.acorn.api.dto.board.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeReqDTO {

    @NotNull
    @Positive
    private Integer boardId;
}