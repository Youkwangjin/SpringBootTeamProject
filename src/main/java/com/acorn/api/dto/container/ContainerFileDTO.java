package com.acorn.api.dto.container;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContainerFileDTO {

    private Integer containerFileId;

    private Integer containerId;

    private String containerOriginalFileName;

    private String containerStoredFileName;

    private String containerFilePath;

    private String containerFileExtNm;

    private String containerFileSize;
}