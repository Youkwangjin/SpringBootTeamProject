package com.acorn.api.dto.notice.request;

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
public class NoticeSaveReqDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String noticeTitle;

    @NotBlank
    private String noticeContents;

    private List<MultipartFile> noticeFiles;
}