package com.acorn.api.service.admin.impl;

import com.acorn.api.code.admin.ApiAdminErrorCode;
import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.owner.ApiOwnerErrorCode;
import com.acorn.api.dto.admin.AdminOwnerDeleteRequestDTO;
import com.acorn.api.dto.admin.AdminOwnerListDTO;
import com.acorn.api.dto.admin.AdminOwnerUpdateRequestDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.service.admin.AdminOwnerService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOwnerServiceImpl implements AdminOwnerService {

    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;
    private final ContainerRepository containerRepository;

    @Override
    public List<AdminOwnerListDTO> getOwnerList(AdminOwnerListDTO listData) {
        listData.setTotalCount(ownerRepository.selectAdminOwnerListCountByRequest(listData));
        List<Owner> ownerListData = ownerRepository.selectAdminOwnerListData(listData);
        return ownerListData.stream()
                .map(ownerList -> {
                    final Integer rowNum = ownerList.getRowNum();
                    final Integer ownerId = ownerList.getOwnerId();
                    final String ownerBusinessNum = ownerList.getOwnerBusinessNum();
                    final String ownerNm = ownerList.getOwnerNm();
                    final String ownerCompanyName = ownerList.getOwnerCompanyName();
                    final LocalDateTime ownerCreated = ownerList.getOwnerCreated();

                    return AdminOwnerListDTO.builder()
                            .rowNum(rowNum)
                            .ownerId(ownerId)
                            .ownerBusinessNum(ownerBusinessNum)
                            .ownerNm(ownerNm)
                            .ownerCompanyName(ownerCompanyName)
                            .ownerCreated(ownerCreated)
                            .build();

                })
                .collect(Collectors.toList());
    }

    @Override
    public OwnerResponseDTO getOwnerData(Integer ownerId) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiAdminErrorCode.ADMIN_FOUND_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(ownerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        final String ownerEmail = ownerData.getOwnerEmail();
        final String ownerBusinessNum = ownerData.getOwnerBusinessNum();
        final String ownerNm = ownerData.getOwnerNm();
        final String ownerTel = ownerData.getOwnerTel();
        final String ownerCompanyName = ownerData.getOwnerCompanyName();
        final String ownerAddr = ownerData.getOwnerAddr();
        final LocalDateTime ownerCreated = ownerData.getOwnerCreated();
        final LocalDateTime ownerUpdated = ownerData.getOwnerUpdated();

        return OwnerResponseDTO.builder()
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .ownerBusinessNum(ownerBusinessNum)
                .ownerNm(ownerNm)
                .ownerTel(ownerTel)
                .ownerCompanyName(ownerCompanyName)
                .ownerAddr(ownerAddr)
                .ownerCreated(ownerCreated)
                .ownerUpdated(ownerUpdated)
                .build();
    }

    @Override
    @Transactional
    public void adminOwnerUpdate(AdminOwnerUpdateRequestDTO requestData) {
        final Integer ownerId = requestData.getOwnerId();
        final String ownerEmail = requestData.getOwnerEmail();
        final String ownerBusinessNum = requestData.getOwnerBusinessNum();
        final String ownerNm = requestData.getOwnerNm();
        final String ownerTel = requestData.getOwnerTel();
        final String ownerCompanyName = requestData.getOwnerCompanyName();

        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiAdminErrorCode.ADMIN_FOUND_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(ownerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        List<Container> containerData = containerRepository.selectContainerAllData(ownerId);
        for (Container container : containerData) {
            final Integer containerStatus = container.getContainerStatus();
            final Integer containerApprovalStatus = container.getContainerApprovalStatus();

            if (!Objects.equals(containerApprovalStatus, ContainerStatus.CONTAINER_APPROVAL_STATUS_PENDING.getCode())) {
                throw new AcontainerException(ApiOwnerErrorCode.OWNER_UPDATE_ERROR);
            }

            if (!Objects.equals(containerStatus, ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode())) {
                throw new AcontainerException(ApiOwnerErrorCode.OWNER_UPDATE_ERROR);
            }
        }

        Owner updateOwner = Owner.builder()
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .ownerBusinessNum(ownerBusinessNum)
                .ownerNm(ownerNm)
                .ownerTel(ownerTel)
                .ownerCompanyName(ownerCompanyName)
                .build();

        ownerRepository.adminOwnerUpdate(updateOwner);
    }

    @Override
    @Transactional
    public void adminOwnerDelete(AdminOwnerDeleteRequestDTO requestData) {
        final Integer ownerId = requestData.getOwnerId();
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiAdminErrorCode.ADMIN_FOUND_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(ownerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.OWNER_FOUND_ERROR);
        }

        List<Container> containerData = containerRepository.selectContainerAllData(ownerId);
        for (Container container : containerData) {
            final Integer containerId = container.getContainerId();
            final Integer containerStatus = container.getContainerStatus();
            final Integer containerApprovalStatus = container.getContainerApprovalStatus();

            if (!Objects.equals(containerApprovalStatus, ContainerStatus.CONTAINER_APPROVAL_STATUS_PENDING.getCode())) {
                throw new AcontainerException(ApiOwnerErrorCode.OWNER_DELETION_ERROR);
            }

            if (!Objects.equals(containerStatus, ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode())) {
                throw new AcontainerException(ApiOwnerErrorCode.OWNER_DELETION_ERROR);
            }

            Container deleteContainer = Container.builder()
                    .containerId(containerId)
                    .build();

            containerRepository.containerDelete(deleteContainer);
        }

        Owner deleteOwner = Owner.builder()
                .ownerId(ownerId)
                .build();

        ownerRepository.ownerDelete(deleteOwner);
    }
}