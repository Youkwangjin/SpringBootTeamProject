package com.acorn.api.dto.container.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContainerFileResDTO {

    private Integer containerFileId;

    private Integer containerId;

    private String containerOriginalFileName;

    private String containerStoredFileName;

    private String containerFilePath;

    private String containerFileExtNm;

    private String containerFileSize;
}