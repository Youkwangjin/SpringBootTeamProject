package com.acorn.api.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardSaveDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String boardTitle;

    @NotBlank
    private String boardWriter;

    @NotBlank
    @Size(min = 6)
    private String boardPassword;

    @NotBlank
    private String boardContents;

    private List<MultipartFile> boardFiles;
}