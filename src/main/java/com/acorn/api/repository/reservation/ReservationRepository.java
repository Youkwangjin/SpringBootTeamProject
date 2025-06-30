package com.acorn.api.repository.reservation;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.reservation.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationRepository {

    Integer selectReservationIdKey();

    Integer selectListCountByRequest(PaginationRequest pagination);

    List<Reservation> selectReservationListData(PaginationRequest pagination);

    Boolean existsActiveReservationByRequest(@Param("containerId") Integer containerId, @Param("reservationStatus") Integer reservationStatus);

    List<Reservation> selectReservationByUserId(@Param("userId") Integer userId);

    List<Reservation> selectReservationByUserIdAndStatus(@Param("reservationUserId") Integer reservationUserId, @Param("reservationStatus") List<Integer> statusList);

    List<Integer> selectExpiredPendingReservations(LocalDateTime threeDaysAgo);

    Reservation selectReservationDetailData(@Param("reservationId") Integer reservationId);

    Integer selectReservationContainerId(@Param("reservationId") Integer reservationId);

    void insertReservation(Reservation reservation);

    void updateReservationTid(@Param("reservationId") Integer reservationId, @Param("reservationTid") String kakaoPayTid);

    void updateReservation(Reservation reservation);

    void updateReservationStatus(Reservation reservation);
}