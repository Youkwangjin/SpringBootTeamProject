package com.acorn.api.entity.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactFile {

    private Integer contactFileId;

    private Integer contactId;

    private String contactOriginalFileName;

    private String contactStoredFileName;

    private String contactFilePath;

    private String contactFileExtNm;

    private String contactFileSize;

    private LocalDateTime contactFileCreated;

    private LocalDateTime contactFileUpdated;
}