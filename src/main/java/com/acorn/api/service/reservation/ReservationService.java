package com.acorn.api.service.reservation;

import com.acorn.api.dto.reservation.ReservationCancelDTO;
import com.acorn.api.dto.reservation.ReservationDetailDTO;
import com.acorn.api.dto.reservation.ReservationListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {

    List<ReservationListDTO> getReservationListData(ReservationListDTO listData);

    ReservationDetailDTO getReservationData(Integer reservationId);

    void reserveContainer(Integer containerId);

    void reserveCancelContainer(ReservationCancelDTO requestData);
}