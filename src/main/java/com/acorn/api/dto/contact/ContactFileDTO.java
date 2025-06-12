package com.acorn.api.dto.contact;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactFileDTO {

    private Integer contactFileId;

    private Integer contactId;

    private String contactOriginalFileName;

    private String contactStoredFileName;

    private String contactFilePath;

    private String contactFileExtNm;

    private String contactFileSize;
}