package com.acorn.api.dto.notice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class NoticeUpdateDTO {

    @NotNull
    @Positive
    private Integer noticeId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String noticeTitle;

    @NotBlank
    private String noticeContents;

    private List<MultipartFile> noticeFiles;

    private List<Integer> noticeFileIds;
}