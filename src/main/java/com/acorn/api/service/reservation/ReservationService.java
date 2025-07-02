package com.acorn.api.service.reservation;

import com.acorn.api.dto.reservation.request.ReservationContainerListReqDTO;
import com.acorn.api.dto.reservation.response.ReservationContainerListResDTO;
import com.acorn.api.dto.reservation.request.ReservationCancelReqDTO;
import com.acorn.api.dto.reservation.response.ReservationDetailResDTO;
import com.acorn.api.dto.reservation.request.ReservationListReqDTO;
import com.acorn.api.dto.reservation.response.ReservationListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {

    List<ReservationListResDTO> getReservationListData(ReservationListReqDTO listData);

    List<ReservationContainerListResDTO> getReservationContainerListData(ReservationContainerListReqDTO listData);

    ReservationDetailResDTO getReservationData(Integer reservationId);

    void reserveContainer(Integer containerId);

    void reserveCancelContainer(ReservationCancelReqDTO requestData);
}