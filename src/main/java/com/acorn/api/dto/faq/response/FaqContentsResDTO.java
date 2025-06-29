package com.acorn.api.dto.faq.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaqContentsResDTO {

    private Integer faqId;

    private String faqContents;
}