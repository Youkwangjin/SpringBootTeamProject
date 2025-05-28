package com.acorn.api.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentApproveRequestDTO {

    @NotNull
    @Positive
    private Integer reservationId;

    @NotBlank
    private String pg_token;
}