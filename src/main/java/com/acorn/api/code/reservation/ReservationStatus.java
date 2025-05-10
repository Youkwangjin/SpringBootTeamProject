package com.acorn.api.code.reservation;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    RESERVATION_STATUS_PENDING(0, "예약 대기"),
    RESERVATION_STATUS_ACTIVE(1, "예약 활성"),
    RESERVATION_STATUS_COMPLETED(2, "예약 종료"),
    RESERVATION_STATUS_CANCELLED(3, "예약 취소");

    private final Integer code;
    private final String description;

    ReservationStatus(final Integer code, final String description) {
        this.code = code;
        this.description = description;
    }
}