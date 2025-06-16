package com.acorn.api.dto.payment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentApproveReqDTO {

    @NotNull
    @Positive
    private Integer reservationId;

    @NotBlank
    private String pg_token;
}