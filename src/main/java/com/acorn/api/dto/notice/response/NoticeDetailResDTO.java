package com.acorn.api.dto.notice.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class NoticeDetailResDTO {

    private Integer noticeId;

    private String noticeTitle;

    private String noticeWriter;

    private String noticeContents;

    private String noticeContentsText;

    private Integer noticeHits;

    private Integer noticeAdminId;

    private LocalDateTime noticeCreated;

    private List<NoticeFileResDTO> noticeFiles;
}