package com.acorn.api.repository.reservation;

import com.acorn.api.entity.reservation.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationRepository {

    List<Reservation> selectReservationByUserId(@Param("userId") Integer userId);
}