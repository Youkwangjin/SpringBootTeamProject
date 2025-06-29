package com.acorn.api.dto.container.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContainerReservationListResDTO {

    private Integer containerId;

    private String containerName;

    private String userNm;

    private Integer reservationStatus;

    private Integer paymentStatus;
}