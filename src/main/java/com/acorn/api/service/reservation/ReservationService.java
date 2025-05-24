package com.acorn.api.service.reservation;

import com.acorn.api.dto.reservation.ReservationListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {

    List<ReservationListDTO> getReservationListData(ReservationListDTO listData);

    void reserveContainer(Integer containerId);
}