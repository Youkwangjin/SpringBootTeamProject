package com.acorn.api.dto.payment.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentListResDTO {

    private Integer rowNum;

    private Integer paymentId;

    private Integer paymentUserId;

    private Integer paymentAmount;

    private Integer paymentStatus;

    private String containerName;

    private LocalDateTime paymentApproved;

    private LocalDateTime paymentCanceled;
}