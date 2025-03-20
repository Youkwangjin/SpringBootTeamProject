package com.acorn.api.dto.container;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ContainerDetailDTO {

    private Integer containerId;

    private Integer ownerId;

    private String ownerName;

    private String containerName;

    private String containerAddr;

    private BigDecimal containerLatitude;

    private BigDecimal containerLongitude;

    private String containerContents;

    private String containerContentsText;

    private BigDecimal containerSize;

    private Integer containerPrice;

    private Integer containerStatus;

    private List<ContainerFileDTO> containerFiles;
}