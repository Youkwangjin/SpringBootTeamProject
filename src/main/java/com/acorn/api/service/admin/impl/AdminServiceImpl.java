package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.admin.AdminContainerDetailResponseDTO;
import com.acorn.api.dto.admin.AdminResponseDTO;
import com.acorn.api.dto.container.ContainerDetailDTO;
import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.entity.container.Container;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.service.admin.AdminService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ContainerRepository containerRepository;

    @Override
    public AdminResponseDTO getAdminData() {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        final Integer adminId = adminData.getAdminId();
        final String adminEmail = adminData.getAdminEmail();
        final String adminPassword = adminData.getAdminPassword();
        final String adminNm = adminData.getAdminNm();

        return AdminResponseDTO.builder()
                .adminId(adminId)
                .adminEmail(adminEmail)
                .adminPassword(adminPassword)
                .adminNm(adminNm)
                .build();
    }

    @Override
    public List<ContainerListDTO> getContainerList(ContainerListDTO listData) {
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

                    return ContainerListDTO.builder()
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
    public AdminContainerDetailResponseDTO getContainerData(Integer containerId) {
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

        final String businessNum = containerData.getOwner().getOwnerBusinessNum();
        final String ownerNm = containerData.getOwner().getOwnerNm();
        final String companyName = containerData.getOwner().getOwnerCompanyName();
        final String ownerTel = containerData.getOwner().getOwnerTel();

        final ContainerDetailDTO containerDTO = ContainerDetailDTO.builder()
                .containerId(containerId)
                .containerName(containerName)
                .containerSize(containerSize)
                .containerPrice(containerPrice)
                .containerAddr(containerAddr)
                .containerStatus(containerStatus)
                .containerApprovalStatus(containerApprovalStatus)
                .containerContents(containerContents)
                .build();

        final OwnerResponseDTO ownerDTO = OwnerResponseDTO.builder()
                .ownerBusinessNum(businessNum)
                .ownerNm(ownerNm)
                .ownerCompanyName(companyName)
                .ownerTel(ownerTel)
                .build();

        return AdminContainerDetailResponseDTO.builder()
                .container(containerDTO)
                .owner(ownerDTO)
                .build();
    }
}