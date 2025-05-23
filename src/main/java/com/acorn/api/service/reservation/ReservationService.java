package com.acorn.api.service.reservation;

import org.springframework.stereotype.Service;

@Service
public interface ReservationService {
    void reserveContainer(Integer containerId);
}