package com.acorn.api.dto.contact.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ContactDetailResDTO {

    private Integer contactId;

    private Integer contactUserId;

    private Integer contactOwnerId;

    private String contactTitle;

    private String contactContents;

    private String contactContentsText;

    private Integer contactStatus;

    private Integer contactWriterType;

    private String contactAdminContents;

    private String contactAnswerYn;

    private LocalDateTime contactCreated;

    private List<ContactFileResDTO> contactFiles;
}