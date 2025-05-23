package com.acorn.api.repository.reservation;

import com.acorn.api.entity.reservation.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationRepository {

    Integer selectReservationIdKey();

    Boolean selectActiveReservationsCountByRequest(@Param("containerId") Integer containerId, @Param("reservationStatus") Integer reservationStatus);

    List<Reservation> selectReservationByUserId(@Param("userId") Integer userId);

    void insertReservation(Reservation reservation);
}