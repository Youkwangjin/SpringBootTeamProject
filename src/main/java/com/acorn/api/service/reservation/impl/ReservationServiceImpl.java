package com.acorn.api.service.reservation.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.dto.reservation.ReservationDetailDTO;
import com.acorn.api.dto.reservation.ReservationListDTO;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ContainerRepository containerRepository;

    @Override
    public List<ReservationListDTO> getReservationListData(ReservationListDTO listData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        listData.setReservationUserId(currentUserId);
        listData.setTotalCount(reservationRepository.selectListCountByRequest(listData));
        List<Reservation> reservationListData = reservationRepository.selectReservationListData(listData);

        return reservationListData.stream()
                .map(reservationList -> {
                    final Integer rowNum = reservationList.getRowNum();
                    final Integer reservationId = reservationList.getReservationId();
                    final Integer reservationUserId = reservationList.getReservationUserId();
                    final String containerName = reservationList.getContainer().getContainerName();
                    final String companyName = reservationList.getContainer().getOwner().getOwnerCompanyName();
                    final Integer reservationStatus = reservationList.getReservationStatus();
                    final LocalDateTime reservationStartDate = reservationList.getReservationStartDate();
                    final LocalDateTime reservationEndDate = reservationList.getReservationEndDate();
                    final LocalDateTime reservationCreated = reservationList.getReservationCreated();

                    return ReservationListDTO.builder()
                            .rowNum(rowNum)
                            .reservationId(reservationId)
                            .reservationUserId(reservationUserId)
                            .containerName(containerName)
                            .companyName(companyName)
                            .reservationStatus(reservationStatus)
                            .reservationStartDate(reservationStartDate)
                            .reservationEndDate(reservationEndDate)
                            .reservationCreated(reservationCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDetailDTO getReservationData(Integer reservationId) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Reservation reservationDetailData = reservationRepository.selectReservationDetailData(reservationId);
        if (reservationDetailData == null) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_NOT_FOUND);
        }

        final Integer reservationUserId = reservationDetailData.getReservationUserId();
        final Integer reservationContainerId = reservationDetailData.getReservationContainerId();
        final Integer reservationStatus = reservationDetailData.getReservationStatus();
        final String containerName = reservationDetailData.getContainer().getContainerName();
        final String containerAddr = reservationDetailData.getContainer().getContainerAddr();
        final Integer containerPrice = reservationDetailData.getContainer().getContainerPrice();
        final String ownerNm = reservationDetailData.getContainer().getOwner().getOwnerNm();
        final String companyName = reservationDetailData.getContainer().getOwner().getOwnerCompanyName();
        final LocalDateTime reservationStartDate = reservationDetailData.getReservationStartDate();
        final LocalDateTime reservationEndDate = reservationDetailData.getReservationEndDate();

        if (!Objects.equals(currentUserId, reservationUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        return ReservationDetailDTO.builder()
                .reservationId(reservationId)
                .reservationUserId(reservationUserId)
                .reservationContainerId(reservationContainerId)
                .reservationStatus(reservationStatus)
                .containerName(containerName)
                .containerAddr(containerAddr)
                .containerPrice(containerPrice)
                .ownerNm(ownerNm)
                .companyName(companyName)
                .reservationStartDate(reservationStartDate)
                .reservationEndDate(reservationEndDate)
                .build();
    }

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