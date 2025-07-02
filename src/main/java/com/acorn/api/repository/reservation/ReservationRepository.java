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

    Integer selectReservationContainerCountByRequest(PaginationRequest pagination);

    List<Reservation> selectReservationListData(PaginationRequest pagination);

    List<Reservation> selectReservationContainerListData(PaginationRequest pagination);

    List<Reservation> selectReservationByUserId(@Param("userId") Integer userId);

    List<Reservation> selectReservationByUserIdAndStatus(@Param("reservationUserId") Integer reservationUserId, @Param("reservationStatus") List<Integer> statusList);

    List<Integer> selectExpiredPendingReservations(LocalDateTime threeDaysAgo);

    List<Integer> selectExpiredReservation(LocalDateTime todayNoon);

    Reservation selectReservationDetailData(@Param("reservationId") Integer reservationId);

    Reservation selectReservationContainerDetailData(@Param("reservationId") Integer reservationId);

    Integer selectReservationContainerId(@Param("reservationId") Integer reservationId);

    Boolean existsActiveReservationByRequest(@Param("containerId") Integer containerId, @Param("reservationStatus") Integer reservationStatus);

    void insertReservation(Reservation reservation);

    void updateReservationTid(@Param("reservationId") Integer reservationId, @Param("reservationTid") String kakaoPayTid);

    void updateReservation(Reservation reservation);

    void updateReservationStatus(Reservation reservation);
}