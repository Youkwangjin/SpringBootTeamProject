package com.acorn.api.scheduler.reservation;

import com.acorn.api.scheduler.service.ReservationMigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationStatusScheduler {

    private final ReservationMigrationService reservationMigrationService;

    @Scheduled(cron = "0 0 16 * * *")
    public void updateReservationStatus() {
        reservationMigrationService.autoCancelExpiredUnpaidReservations();
    }
}