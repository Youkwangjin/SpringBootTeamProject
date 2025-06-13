package com.acorn.api.dto.board.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardSaveReqDTO {

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