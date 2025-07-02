package com.acorn.api.dto.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationContainerDetailResDTO {

    private Integer reservationStatus;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    private Integer containerId;

    private String containerName;

    private Integer containerPrice;

    private String userNm;

    private String userTel;

    private Integer paymentStatus;

    private Integer paymentAmount;

    private LocalDateTime paymentApproved;

    private LocalDateTime paymentCanceled;
}