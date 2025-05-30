package com.acorn.api.entity.payment;

import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    private Integer rowNum;

    private Integer paymentId;

    private String paymentTid;

    private Integer paymentUserId;

    private Integer paymentReservationId;

    private Integer paymentAmount;

    private Integer paymentStatus;

    private LocalDateTime paymentApproved;

    private LocalDateTime paymentCreated;

    private LocalDateTime paymentCanceled;

    private LocalDateTime paymentCancelDeadline;

    private User user;

    private Reservation reservation;
}