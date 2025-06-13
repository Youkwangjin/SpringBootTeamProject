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
public class PaymentCancelReqDTO {

    @NotNull
    @Positive
    private Integer paymentId;

    @NotBlank
    private String paymentTid;
}