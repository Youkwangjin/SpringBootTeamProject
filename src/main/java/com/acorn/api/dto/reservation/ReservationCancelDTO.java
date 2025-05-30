package com.acorn.api.dto.reservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelDTO {

    @NotNull
    @Positive
    private Integer reservationId;

    @NotNull
    @Positive
    private Integer reservationContainerId;
}