package com.acorn.api.scheduler.service.impl;

import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.reservation.ReservationRepository;
import com.acorn.api.scheduler.service.ReservationMigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationMigrationServiceImpl implements ReservationMigrationService {

    private final ReservationRepository reservationRepository;
    private final ContainerRepository containerRepository;

    @Override
    @Transactional
    public void autoCancelExpiredUnpaidReservations() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTimeMillis = System.currentTimeMillis();
        log.info("=============== Migration Reservation Cancel Data Start : {}  ===============", sdf.format(new Date(startTimeMillis)));
        log.info("");

        final LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        List<Integer> reservationIds = reservationRepository.selectExpiredPendingReservations(threeDaysAgo);

        log.info("만료된 미결제 예약 건수 : {}건", reservationIds.size());

        for (Integer reservationId : reservationIds) {
            try {
                Integer containerId = reservationRepository.selectReservationContainerId(reservationId);

                Reservation updateReservationStatus = Reservation.builder()
                        .reservationId(reservationId)
                        .reservationStatus(ReservationStatus.RESERVATION_STATUS_CANCELLED.getCode())
                        .build();

                reservationRepository.updateReservationStatus(updateReservationStatus);

                Container updatedContainer = Container.builder()
                        .containerId(containerId)
                        .containerStatus(ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode())
                        .build();

                containerRepository.updateContainerStatus(updatedContainer);
                log.info("예약 ID {} 및 창고 {} 취소/상태변경 완료", reservationId, containerId);
            } catch (Exception e) {
                log.warn("예약 ID {} 취소 처리 실패: {}", reservationId, e.getMessage());
            }
        }

        long endTimeMillis = System.currentTimeMillis();
        log.info("");
        log.info("=============== Migration Reservation Data End   : {} (소요: {} ms) ===============", sdf.format(new Date(endTimeMillis)), (endTimeMillis - startTimeMillis));
    }

    @Override
    @Transactional
    public void autoExpireEndedReservations() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTimeMillis = System.currentTimeMillis();
        log.info("=============== Migration Reservation Expire Data Start : {}  ===============", sdf.format(new Date(startTimeMillis)));
        log.info("");

        final LocalDateTime todayNoon = LocalDate.now().atTime(12, 0);
        List<Integer> reservationIds = reservationRepository.selectExpiredReservation(todayNoon);

        for (Integer reservationId : reservationIds) {
            try {
                Integer containerId = reservationRepository.selectReservationContainerId(reservationId);

                Reservation updateReservationStatus = Reservation.builder()
                        .reservationId(reservationId)
                        .reservationStatus(ReservationStatus.RESERVATION_STATUS_COMPLETED.getCode())
                        .build();

                reservationRepository.updateReservationStatus(updateReservationStatus);

                Container updatedContainer = Container.builder()
                        .containerId(containerId)
                        .containerStatus(ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode())
                        .build();

                containerRepository.updateContainerStatus(updatedContainer);
                log.info("예약 ID {} 및 창고 {} 종료/상태변경 완료", reservationId, containerId);
            } catch (Exception e) {
                log.warn("예약 ID {} 종료 처리 실패: {}", reservationId, e.getMessage());
            }
        }

        long endTimeMillis = System.currentTimeMillis();
        log.info("");
        log.info("=============== Migration Reservation Expire Data End   : {} (소요: {} ms) ===============", sdf.format(new Date(endTimeMillis)), (endTimeMillis - startTimeMillis));
    }
}