package com.acorn.api.dto.notice.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeFileResDTO {

    private Integer noticeFileId;

    private Integer noticeId;

    private String noticeOriginalFileName;

    private String noticeStoredFileName;

    private String noticeFilePath;

    private String noticeFileExtNm;

    private String noticeFileSize;
}