package com.acorn.api.dto.reservation.response;

import com.acorn.api.dto.common.CommonListReqDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationListResDTO extends CommonListReqDTO {

    private Integer rowNum;

    private Integer reservationId;

    private Integer reservationUserId;

    private Integer reservationContainerId;

    private String containerName;

    private String companyName;

    private Integer reservationStatus;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    private LocalDateTime reservationCreated;
}