package com.acorn.api.dto.payment.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentReadyReqDTO {

    @NotNull
    @Positive
    private Integer reservationId;
}