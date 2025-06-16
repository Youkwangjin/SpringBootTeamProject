package com.acorn.api.dto.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationDetailResDTO {

    private Integer reservationId;

    private Integer reservationUserId;

    private Integer reservationContainerId;

    private Integer paymentId;

    private String containerName;

    private String containerAddr;

    private Integer containerPrice;

    private String ownerNm;

    private String companyName;

    private Integer reservationStatus;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;
}