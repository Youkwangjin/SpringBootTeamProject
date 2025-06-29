package com.acorn.api.entity.container;

import com.acorn.api.entity.owner.Owner;
import com.acorn.api.entity.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Container {

    private Integer rowNum;

    private Integer containerId;

    private String containerName;

    private String containerAddr;

    private BigDecimal containerLatitude;

    private BigDecimal containerLongitude;

    private String containerContents;

    private String containerContentsText;

    private BigDecimal containerSize;

    private Integer containerPrice;

    private Integer containerStatus;

    private Integer containerApprovalStatus;

    private LocalDateTime containerCreated;

    private LocalDateTime containerUpdated;

    private Integer containerOwnerId;

    private List<ContainerFile> containerFiles;

    private Owner owner;

    private Reservation reservation;
}