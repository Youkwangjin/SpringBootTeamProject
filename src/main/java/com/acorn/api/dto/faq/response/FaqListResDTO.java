package com.acorn.api.dto.faq.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FaqListResDTO {

    private Integer rowNum;

    private Integer faqId;

    private String faqTitle;

    private String faqContents;

    private String faqContentsText;

    private LocalDateTime faqCreated;

    private LocalDateTime faqUpdated;
}