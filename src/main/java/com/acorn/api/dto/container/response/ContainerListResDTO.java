package com.acorn.api.dto.container.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ContainerListResDTO {

    private Integer rowNum;

    private Integer containerId;

    private Integer containerOwnerId;

    private String containerName;

    private String containerAddr;

    private String companyName;

    private BigDecimal containerSize;

    private Integer containerStatus;

    private Integer containerApprovalStatus;

    private LocalDateTime containerCreated;
}