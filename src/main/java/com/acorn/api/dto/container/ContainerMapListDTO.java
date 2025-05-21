package com.acorn.api.dto.container;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ContainerMapListDTO {

    private Integer containerId;

    private Integer containerOwnerId;

    private String containerName;

    private String containerAddr;

    private String companyName;

    private BigDecimal containerSize;

    private Integer containerPrice;

    private String containerContents;

    private String containerContentsText;

    private BigDecimal containerLatitude;

    private BigDecimal containerLongitude;

    private Integer containerStatus;

    private String containerStatusText;

    private Integer containerApprovalStatus;

    private String containerApprovalStatusText;

    private LocalDateTime containerCreated;

    private String containerCreatedText;
}