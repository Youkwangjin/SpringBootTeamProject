package com.acorn.api.entity.container;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContainerFile {

    private Integer containerFileId;

    private Integer containerId;

    private String containerOriginalFileName;

    private String containerStoredFileName;

    private String containerFilePath;

    private String containerFileExtNm;

    private String containerFileSize;

    private LocalDateTime containerFileCreated;

    private LocalDateTime containerFileUpdated;

    private Container container;
}