package com.acorn.api.dto.contact;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ContactDetailDTO {

    private Integer contactId;

    private Integer contactUserId;

    private Integer contactOwnerId;

    private Integer contactAdminId;

    private String contactTitle;

    private String contactContents;

    private String contactContentsText;

    private Integer contactStatus;

    private Integer contactWriterType;

    private String contactAdminContents;

    private String contactAnswerYn;

    private LocalDateTime contactCreated;

    private List<ContactFileDTO> contactFiles;
}