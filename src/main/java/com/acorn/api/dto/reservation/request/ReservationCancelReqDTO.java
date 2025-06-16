package com.acorn.api.dto.reservation.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationCancelReqDTO {

    @NotNull
    @Positive
    private Integer reservationId;

    @NotNull
    @Positive
    private Integer reservationContainerId;
}