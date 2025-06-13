package com.acorn.api.dto.board.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateReqDTO {

    @NotNull
    @Positive
    private Integer boardId;

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

    private Integer boardUserId;

    private Integer boardOwnerId;

    private List<MultipartFile> boardFiles;

    private List<Integer> boardFileIds;
}