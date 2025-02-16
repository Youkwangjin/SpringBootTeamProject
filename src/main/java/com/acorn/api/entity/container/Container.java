package com.acorn.api.entity.container;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Container {

    private Integer rowNum;

    private Integer containerId;

    private String containerName;

    private String containerAddr;

    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal containerLatitude;

    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal containerLongitude;

    private String containerContents;

    private String containerContentsText;

    private Integer containerSize;

    private Integer containerPrice;

    private Integer containerStatus;

    private Integer containerApprovalStatus;

    private LocalDateTime containerCreated;

    private LocalDateTime containerUpdated;
}