package com.acorn.api.entity.faq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Faq {

    private Integer rowNum;

    private Integer faqId;

    private Integer faqAdminId;

    private String faqTitle;

    private String faqContents;

    private String faqContentsText;

    private LocalDateTime faqCreated;

    private LocalDateTime faqUpdated;
}