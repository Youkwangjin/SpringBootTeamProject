package com.acorn.api.entity.reservation;

import com.acorn.api.entity.container.Container;
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
public class Reservation {

    private Integer rowNum;

    private Integer reservationId;

    private Integer reservationStatus;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    private LocalDateTime reservationCreated;

    private LocalDateTime reservationUpdated;

    private User user;

    private Container container;
}