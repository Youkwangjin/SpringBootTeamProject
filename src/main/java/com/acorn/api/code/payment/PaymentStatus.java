package com.acorn.api.code.payment;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAYMENT_STATUS_PENDING(0, "결제 대기"),
    PAYMENT_STATUS_COMPLETED(1, "결제 완료"),
    PAYMENT_STATUS_CANCELLED(2, "결제 취소");

    private final Integer code;
    private final String description;

    PaymentStatus(final Integer code, final String description) {
        this.code = code;
        this.description = description;
    }
}