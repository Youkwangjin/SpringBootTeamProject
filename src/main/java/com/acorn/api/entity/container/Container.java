package com.acorn.api.entity.container;

import com.acorn.api.entity.owner.Owner;
import com.acorn.api.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private Integer containerUserId;

    private Integer containerOwnerId;

    private User user;

    private Owner owner;
}