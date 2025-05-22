package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.dto.admin.ContainerManagementRequestDTO;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.service.admin.AdminContainerService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminContainerServiceImpl implements AdminContainerService {

    private final ContainerRepository containerRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public void processCancelReview(ContainerManagementRequestDTO requestData) {
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
}