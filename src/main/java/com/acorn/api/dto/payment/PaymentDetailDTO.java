package com.acorn.api.dto.payment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentDetailDTO {

    private Integer paymentId;

    private String paymentTid;

    private Integer paymentUserId;

    private Integer paymentAmount;

    private Integer paymentStatus;

    private String containerName;

    private LocalDateTime paymentApproved;

    private LocalDateTime paymentCanceled;

    private LocalDateTime paymentCancelDeadline;
}