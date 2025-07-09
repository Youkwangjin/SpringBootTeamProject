package com.acorn.api.dto.board.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentReplySaveReqDTO {

    @NotNull
    @Positive
    private Integer boardId;

    @NotNull
    @Positive
    private Integer boardCommentId;

    @NotBlank
    private String boardCommentContents;

    @Pattern(regexp = "^[YN]$")
    @NotBlank
    private String boardCommentYn;
}