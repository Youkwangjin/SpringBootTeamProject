package com.acorn.api.dto.notice.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeListResDTO {

    private Integer rowNum;

    private Integer noticeId;

    private String noticeTitle;

    private String noticeWriter;

    private Integer noticeHits;

    private LocalDateTime noticeCreated;
}