package com.acorn.api.dto.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentReadyRequestDTO {

    @NotNull
    @Positive
    private Integer reservationId;
}