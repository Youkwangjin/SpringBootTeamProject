package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.dto.admin.response.AdminContainerDetailResDTO;
import com.acorn.api.dto.admin.request.AdminContainerManagementReqDTO;
import com.acorn.api.dto.container.response.ContainerDetailResDTO;
import com.acorn.api.dto.container.request.ContainerListReqDTO;
import com.acorn.api.dto.container.response.ContainerListResDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.service.admin.AdminContainerService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminContainerServiceImpl implements AdminContainerService {

    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;
    private final ContainerRepository containerRepository;

    @Override
    public List<ContainerListResDTO> getContainerList(ContainerListReqDTO listData) {
        listData.setTotalCount(containerRepository.selectAdminListCountByRequest(listData));
        List<Container> containerListData = containerRepository.selectAdminContainerListData(listData);

        return containerListData.stream()
                .map(containerList -> {
                    final Integer rowNum = containerList.getRowNum();
                    final Integer containerId = containerList.getContainerId();
                    final String containerAddr = containerList.getContainerAddr();
                    final String companyName = containerList.getOwner().getOwnerCompanyName();
                    final BigDecimal containerSize = containerList.getContainerSize();
                    final Integer containerStatus = containerList.getContainerStatus();
                    final Integer containerApprovalStatus = containerList.getContainerApprovalStatus();

                    return ContainerListResDTO.builder()
                            .rowNum(rowNum)
                            .containerId(containerId)
                            .containerAddr(containerAddr)
                            .companyName(companyName)
                            .containerSize(containerSize)
                            .containerStatus(containerStatus)
                            .containerApprovalStatus(containerApprovalStatus)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public AdminContainerDetailResDTO getContainerData(Integer containerId) {
        Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(containerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final String containerName = containerData.getContainerName();
        final BigDecimal containerSize = containerData.getContainerSize();
        final Integer containerPrice = containerData.getContainerPrice();
        final String containerAddr = containerData.getContainerAddr();
        final String containerContents = containerData.getContainerContents();
        final Integer containerStatus = containerData.getContainerStatus();
        final Integer containerApprovalStatus = containerData.getContainerApprovalStatus();

        final Integer ownerId = containerData.getOwner().getOwnerId();
        final String businessNum = containerData.getOwner().getOwnerBusinessNum();
        final String ownerNm = containerData.getOwner().getOwnerNm();
        final String companyName = containerData.getOwner().getOwnerCompanyName();
        final String ownerTel = containerData.getOwner().getOwnerTel();

        final ContainerDetailResDTO containerDTO = ContainerDetailResDTO.builder()
                .containerId(containerId)
                .containerName(containerName)
                .containerSize(containerSize)
                .containerPrice(containerPrice)
                .containerAddr(containerAddr)
                .containerStatus(containerStatus)
                .containerApprovalStatus(containerApprovalStatus)
                .containerContents(containerContents)
                .build();

        final OwnerResDTO ownerDTO = OwnerResDTO.builder()
                .ownerId(ownerId)
                .ownerBusinessNum(businessNum)
                .ownerNm(ownerNm)
                .ownerCompanyName(companyName)
                .ownerTel(ownerTel)
                .build();

        return AdminContainerDetailResDTO.builder()
                .container(containerDTO)
                .owner(ownerDTO)
                .build();
    }

    @Override
    @Transactional
    public void processReviewRequest(AdminContainerManagementReqDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer requestOwnerId = requestData.getOwnerId();
        final Integer requestContainerId = requestData.getContainerId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(requestOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(requestContainerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer currentOwnerId = containerData.getOwner().getOwnerId();
        if (!Objects.equals(requestOwnerId, currentOwnerId)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_OWNER_MISMATCH);
        }

        final Integer reviewStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_IN_REVIEW.getCode();
        final Integer unavailableStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
        final Integer currentContainerApprovalStatus = containerData.getContainerApprovalStatus();
        final Integer currentContainerStatus = containerData.getContainerStatus();

        if (Objects.equals(reviewStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_ALREADY_REVIEW);
        }

        if (!Objects.equals(unavailableStatus, currentContainerStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_REVIEW_NOT_AVAILABLE);
        }

        Container updateApprovalStatus = Container.builder()
                .containerId(requestContainerId)
                .containerApprovalStatus(reviewStatus)
                .build();

        containerRepository.updateContainerApproval(updateApprovalStatus);
    }

    @Override
    @Transactional
    public void processApprovalRequest(AdminContainerManagementReqDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer requestOwnerId = requestData.getOwnerId();
        final Integer requestContainerId = requestData.getContainerId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(requestOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(requestContainerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer currentOwnerId = containerData.getOwner().getOwnerId();
        if (!Objects.equals(requestOwnerId, currentOwnerId)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_OWNER_MISMATCH);
        }

        final Integer reviewStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_IN_REVIEW.getCode();
        final Integer approvedStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_APPROVED.getCode();
        final Integer availableStatus = ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode();
        final Integer unavailableStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
        final Integer currentContainerApprovalStatus = containerData.getContainerApprovalStatus();
        final Integer currentContainerStatus = containerData.getContainerStatus();

        if (Objects.equals(approvedStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_ALREADY_APPROVED);
        }

        if (!Objects.equals(reviewStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_APPROVAL_NOT_REVIEW);
        }

        if (!Objects.equals(unavailableStatus, currentContainerStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_STATUS_NOT_UNAVAILABLE_FOR_APPROVAL);
        }

        Container updateStatus = Container.builder()
                .containerId(requestContainerId)
                .containerStatus(availableStatus)
                .containerApprovalStatus(approvedStatus)
                .build();

        containerRepository.updateContainerApprovalAndStatus(updateStatus);
    }

    @Override
    @Transactional
    public void processRejectRequest(AdminContainerManagementReqDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer requestOwnerId = requestData.getOwnerId();
        final Integer requestContainerId = requestData.getContainerId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(requestOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(requestContainerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer currentOwnerId = containerData.getOwner().getOwnerId();
        if (!Objects.equals(requestOwnerId, currentOwnerId)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_OWNER_MISMATCH);
        }

        final Integer reviewStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_IN_REVIEW.getCode();
        final Integer rejectedStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_REJECTED.getCode();
        final Integer unavailableStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
        final Integer currentContainerApprovalStatus = containerData.getContainerApprovalStatus();
        final Integer currentContainerStatus = containerData.getContainerStatus();

        if (Objects.equals(rejectedStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_ALREADY_REJECT);
        }

        if (!Objects.equals(reviewStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_REJECT_NOT_REVIEW);
        }

        if (!Objects.equals(unavailableStatus, currentContainerStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_STATUS_NOT_UNAVAILABLE_FOR_REJECT);
        }

        Container updateStatus = Container.builder()
                .containerId(requestContainerId)
                .containerStatus(unavailableStatus)
                .containerApprovalStatus(rejectedStatus)
                .build();

        containerRepository.updateContainerApprovalAndStatus(updateStatus);
    }

    @Override
    public void processCancelReview(AdminContainerManagementReqDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer requestOwnerId = requestData.getOwnerId();
        final Integer requestContainerId = requestData.getContainerId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(requestOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(requestContainerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer currentOwnerId = containerData.getOwner().getOwnerId();
        if (!Objects.equals(requestOwnerId, currentOwnerId)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_OWNER_MISMATCH);
        }

        final Integer reviewStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_IN_REVIEW.getCode();
        final Integer pendingStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_PENDING.getCode();
        final Integer unavailableStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
        final Integer currentContainerApprovalStatus = containerData.getContainerApprovalStatus();
        final Integer currentContainerStatus = containerData.getContainerStatus();

        if (!Objects.equals(reviewStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_REVIEW_CANCEL_NOT_REVIEW);
        }

        if (!Objects.equals(unavailableStatus, currentContainerStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_REVIEW_CANCEL_NOT_REVIEW);
        }

        Container updateApprovalStatus = Container.builder()
                .containerId(requestContainerId)
                .containerApprovalStatus(pendingStatus)
                .build();

        containerRepository.updateContainerApproval(updateApprovalStatus);
    }

    @Override
    @Transactional
    public void processCancelApproval(AdminContainerManagementReqDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer requestOwnerId = requestData.getOwnerId();
        final Integer requestContainerId = requestData.getContainerId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(requestOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(requestContainerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer currentOwnerId = containerData.getOwner().getOwnerId();
        if (!Objects.equals(requestOwnerId, currentOwnerId)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_OWNER_MISMATCH);
        }

        final Integer approvedStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_APPROVED.getCode();
        final Integer reviewStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_IN_REVIEW.getCode();
        final Integer availableStatus = ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode();
        final Integer unavailableStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
        final Integer currentContainerApprovalStatus = containerData.getContainerApprovalStatus();
        final Integer currentContainerStatus = containerData.getContainerStatus();

        if (!Objects.equals(availableStatus, currentContainerStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_AVAILABLE_CANCEL);
        }

        if (!Objects.equals(approvedStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_APPROVAL_NOT_APPROVED);
        }

        Container updateStatus = Container.builder()
                .containerId(requestContainerId)
                .containerStatus(unavailableStatus)
                .containerApprovalStatus(reviewStatus)
                .build();

        containerRepository.updateContainerApprovalAndStatus(updateStatus);
    }

    @Override
    @Transactional
    public void processCancelReject(AdminContainerManagementReqDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer requestOwnerId = requestData.getOwnerId();
        final Integer requestContainerId = requestData.getContainerId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(requestOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        Container containerData = containerRepository.selectAdminContainerDetailData(requestContainerId);
        if (containerData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer currentOwnerId = containerData.getOwner().getOwnerId();
        if (!Objects.equals(requestOwnerId, currentOwnerId)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_OWNER_MISMATCH);
        }

        final Integer reviewStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_IN_REVIEW.getCode();
        final Integer rejectedStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_REJECTED.getCode();
        final Integer unavailableStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
        final Integer currentContainerApprovalStatus = containerData.getContainerApprovalStatus();
        final Integer currentContainerStatus = containerData.getContainerStatus();

        if (!Objects.equals(rejectedStatus, currentContainerApprovalStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_APPROVAL_NOT_REJECT);
        }

        if (!Objects.equals(unavailableStatus, currentContainerStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_STATUS_NOT_UNAVAILABLE);
        }

        Container updateStatus = Container.builder()
                .containerId(requestContainerId)
                .containerStatus(unavailableStatus)
                .containerApprovalStatus(reviewStatus)
                .build();

        containerRepository.updateContainerApprovalAndStatus(updateStatus);
    }
}