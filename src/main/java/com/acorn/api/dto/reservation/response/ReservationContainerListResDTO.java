package com.acorn.api.dto.reservation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationContainerListResDTO {

    private Integer reservationId;

    private Integer reservationStatus;

    private String containerName;

    private String userNm;

    private Integer paymentStatus;
}