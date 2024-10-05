package com.acorn.api.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRegisterDTO {

    @NotBlank
    @Size(max = 50)
    private String boardTitle;

    @NotBlank
    private String boardWriter;

    @NotBlank
    @Size(min = 6)
    private String boardPassword;

    @NotBlank
    private String boardContents;
}
