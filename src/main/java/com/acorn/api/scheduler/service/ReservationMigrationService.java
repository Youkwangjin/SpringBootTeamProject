package com.acorn.api.scheduler.service;

import org.springframework.stereotype.Service;

@Service
public interface ReservationMigrationService {

    void autoCancelExpiredUnpaidReservations();

    void autoExpireEndedReservations();
}