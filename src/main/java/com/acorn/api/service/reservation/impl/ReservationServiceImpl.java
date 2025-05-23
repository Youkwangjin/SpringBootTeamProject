package com.acorn.api.service.reservation.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.reservation.ReservationRepository;
import com.acorn.api.service.reservation.ReservationService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ContainerRepository containerRepository;

    @Override
    @Transactional
    public void reserveContainer(Integer containerId) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container exsitedContainer = containerRepository.selectContainerDetailData(containerId);
        if (exsitedContainer == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer pendingStatus = ContainerStatus.CONTAINER_STATUS_PENDING.getCode();
        final Integer reservePendingStatus = ReservationStatus.RESERVATION_STATUS_PENDING.getCode();
        final Integer exsitedContainerApprovalStatus = exsitedContainer.getContainerApprovalStatus();
        final Integer exsitedContainerStatus = exsitedContainer.getContainerStatus();

        Boolean alreadyReserved = reservationRepository.selectActiveReservationsCountByRequest(containerId, reservePendingStatus);
        if (alreadyReserved) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_ALREADY);
        }

        if (!Objects.equals(exsitedContainerApprovalStatus, ContainerStatus.CONTAINER_APPROVAL_STATUS_APPROVED.getCode())) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_NOT_APPROVED);
        }

        if (!Objects.equals(exsitedContainerStatus, ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode())) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINERNOT_AVAILABLE);
        }

        final Integer reservationId = reservationRepository.selectReservationIdKey();
        final LocalDateTime reservationStartDate = LocalDateTime.now();
        final LocalDateTime reservationEndDate = reservationStartDate.plusMonths(1).with(LocalTime.NOON);

        Reservation reservationData = Reservation.builder()
                .reservationId(reservationId)
                .reservationUserId(currentUserId)
                .reservationContainerId(containerId)
                .reservationStatus(reservePendingStatus)
                .reservationStartDate(reservationStartDate)
                .reservationEndDate(reservationEndDate)
                .build();

        reservationRepository.insertReservation(reservationData);

        Container updateContainerStatus = Container.builder()
                .containerId(containerId)
                .containerStatus(pendingStatus)
                .build();

        containerRepository.updateContainerStatus(updateContainerStatus);
    }
}