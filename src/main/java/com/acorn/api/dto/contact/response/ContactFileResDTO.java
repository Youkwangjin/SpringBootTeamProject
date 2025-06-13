package com.acorn.api.dto.contact.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactFileResDTO {

    private Integer contactFileId;

    private Integer contactId;

    private String contactOriginalFileName;

    private String contactStoredFileName;

    private String contactFilePath;

    private String contactFileExtNm;

    private String contactFileSize;
}