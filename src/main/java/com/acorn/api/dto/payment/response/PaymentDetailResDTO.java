package com.acorn.api.dto.payment.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentDetailResDTO {

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