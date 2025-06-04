package com.acorn.api.entity.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeFile {

    private Integer noticeFileId;

    private Integer noticeId;

    private String noticeOriginalFileName;

    private String noticeStoredFileName;

    private String noticeFilePath;

    private String noticeFileExtNm;

    private String noticeFileSize;

    private LocalDateTime noticeFileCreated;

    private LocalDateTime noticeFileUpdated;
}