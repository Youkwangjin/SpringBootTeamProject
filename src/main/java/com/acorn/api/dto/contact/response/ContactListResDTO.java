package com.acorn.api.dto.contact.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContactListResDTO {

    private Integer rowNum;

    private Integer contactId;

    private Integer contactUserId;

    private Integer contactOwnerId;

    private String contactTitle;

    private Integer contactStatus;

    private Integer contactWriterType;

    private String contactAnswerYn;

    private LocalDateTime contactCreated;
}