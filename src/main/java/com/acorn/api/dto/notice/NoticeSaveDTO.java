package com.acorn.api.dto.notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class NoticeSaveDTO {

    @NotBlank
    @Size(max = 50)
    private String noticeTitle;

    @NotBlank
    private String noticeContents;

    private List<MultipartFile> noticeFiles;
}