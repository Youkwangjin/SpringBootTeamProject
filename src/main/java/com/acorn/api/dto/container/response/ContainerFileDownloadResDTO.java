package com.acorn.api.dto.container.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContainerFileDownloadResDTO {

    private String originalFileName;

    private byte[] fileBytes;
}
