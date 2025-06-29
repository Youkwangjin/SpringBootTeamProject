package com.acorn.api.service.reservation.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.payment.PaymentStatus;
import com.acorn.api.code.reservation.ReservationStatus;
import com.acorn.api.dto.reservation.request.ReservationCancelReqDTO;
import com.acorn.api.dto.reservation.response.ReservationDetailResDTO;
import com.acorn.api.dto.reservation.request.ReservationListReqDTO;
import com.acorn.api.dto.reservation.response.ReservationListResDTO;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.payment.Payment;
import com.acorn.api.entity.reservation.Reservation;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.payment.PaymentRepository;
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
    private final PaymentRepository paymentRepository;
    private final ContainerRepository containerRepository;

    @Override
    public List<ReservationListResDTO> getReservationListData(ReservationListReqDTO listData) {
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

                    return ReservationListResDTO.builder()
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
    public ReservationDetailResDTO getReservationData(Integer reservationId) {
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

        Payment paymentData = paymentRepository.selectPaymentByReservationId(reservationId);
        if (paymentData == null) {
            throw new AcontainerException(ApiErrorCode.PAYMENT_NOT_FOUND);
        }

        final Integer paymentId = paymentData.getPaymentId();

        return ReservationDetailResDTO.builder()
                .reservationId(reservationId)
                .reservationUserId(reservationUserId)
                .reservationContainerId(reservationContainerId)
                .paymentId(paymentId)
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

        Boolean alreadyReserved = reservationRepository.existsActiveReservationByRequest(containerId, reservePendingStatus);
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

        final Integer paymentId = paymentRepository.selectPaymentIdKey();
        final Integer paymentPendingStatus = PaymentStatus.PAYMENT_STATUS_PENDING.getCode();

        Payment newPayment = Payment.builder()
                .paymentId(paymentId)
                .paymentUserId(currentUserId)
                .paymentReservationId(reservationId)
                .paymentStatus(paymentPendingStatus)
                .build();

        paymentRepository.insertPayment(newPayment);
    }

    @Override
    public void reserveCancelContainer(ReservationCancelReqDTO requestData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer reservationId = requestData.getReservationId();
        final Integer reservationContainerId = requestData.getReservationContainerId();

        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container exsitedContainer = containerRepository.selectContainerDetailData(reservationContainerId);
        if (exsitedContainer == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        Reservation reservationDetailData = reservationRepository.selectReservationDetailData(reservationId);
        if (reservationDetailData == null) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_NOT_FOUND);
        }

        final Integer currentContainerId = exsitedContainer.getContainerId();
        final Integer currentReservationId = reservationDetailData.getReservationId();
        final Integer currentReservationUserId = reservationDetailData.getReservationUserId();
        final Integer currentReservationStatus = reservationDetailData.getReservationStatus();
        final Integer containerAvailableStatus = ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode();
        final Integer reservationPendingStatus = ReservationStatus.RESERVATION_STATUS_PENDING.getCode();
        final Integer reservationCancelStatus = ReservationStatus.RESERVATION_STATUS_CANCELLED.getCode();

        if (!Objects.equals(currentContainerId, reservationContainerId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentReservationId, reservationId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentUserId, currentReservationUserId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentReservationStatus, reservationPendingStatus)) {
            throw new AcontainerException(ApiErrorCode.RESERVE_CONTAINER_NOT_PENDING);
        }

        Payment paymentDetailData = paymentRepository.selectPaymentByReservationId(reservationId);
        if (paymentDetailData != null) {
            final Integer paymentStatus = paymentDetailData.getPaymentStatus();
            final Integer paymentCompletedStatus = PaymentStatus.PAYMENT_STATUS_COMPLETED.getCode();

            if (Objects.equals(paymentStatus, paymentCompletedStatus)) {
                throw new AcontainerException(ApiErrorCode.PAYMENT_ALREADY_COMPLETED);
            }
        }

        Reservation updateStatus = Reservation.builder()
                .reservationId(reservationId)
                .reservationStatus(reservationCancelStatus)
                .build();

        reservationRepository.updateReservationStatus(updateStatus);

        Container updateContainerStatus = Container.builder()
                .containerId(reservationContainerId)
                .containerStatus(containerAvailableStatus)
                .build();

        containerRepository.updateContainerStatus(updateContainerStatus);
    }
}