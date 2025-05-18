package com.acorn.api.service.owner.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.owner.ApiOwnerErrorCode;
import com.acorn.api.dto.owner.OwnerDeleteDTO;
import com.acorn.api.dto.owner.OwnerRegisterDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.dto.owner.OwnerUpdateDTO;
import com.acorn.api.entity.container.Container;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.role.OwnerRole;
import com.acorn.api.service.owner.OwnerService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;
    private final ContainerRepository containerRepository;

    @Override
    public void isOwnerEmailDuplicate(String ownerEmail) {
        Boolean exists = ownerRepository.isEmailDuplicate(ownerEmail);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.EMAIL_DUPLICATED_CONFLICT);
        }
    }

    @Override
    public void isBusinessNumDuplicate(String ownerBusinessNum) {
        Boolean exists = ownerRepository.isBusinessNumDuplicate(ownerBusinessNum);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT);
        }
    }

    @Override
    public void isTelPhoneDuplicate(String ownerTel) {
        Boolean exists = ownerRepository.isTelDuplicate(ownerTel);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.TELPHONE_DUPLICATED_CONFLICT);
        }
    }

    @Override
    public void isCompanyNameDuplicate(String ownerCompanyName) {
        Boolean exists = ownerRepository.isCompanyNameDuplicate(ownerCompanyName);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.COMPANY_NAME_ERROR);
        }
    }

    @Override
    @Transactional
    public void ownerRegister(OwnerRegisterDTO ownerRegisterData) {
        final Integer ownerId = ownerRepository.selectOwnerIdKey();
        final String ownerEmail = ownerRegisterData.getOwnerEmail();
        final String ownerPassword = passwordEncoder.encode(ownerRegisterData.getOwnerPassword());
        final String ownerBusinessNum = ownerRegisterData.getOwnerBusinessNum();
        final String ownerNm = ownerRegisterData.getOwnerNm();
        final String ownerCompanyName = ownerRegisterData.getOwnerCompanyName();
        final String ownerAddr = ownerRegisterData.getOwnerAddr();
        final String ownerTel = ownerRegisterData.getOwnerTel();

        final Owner newRegisterDataOwner = Owner.builder()
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .ownerBusinessNum(ownerBusinessNum)
                .ownerPassword(ownerPassword)
                .ownerNm(ownerNm)
                .ownerCompanyName(ownerCompanyName)
                .ownerAddr(ownerAddr)
                .ownerTel(ownerTel)
                .ownerRole(OwnerRole.OWNER)
                .build();

        ownerRepository.ownerRegister(newRegisterDataOwner);
    }

    @Override
    public OwnerResponseDTO getOwnerData() {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        if (currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(currentOwnerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }
        final Integer ownerId = ownerData.getOwnerId();
        final String ownerEmail = ownerData.getOwnerEmail();
        final String ownerBusinessNum = ownerData.getOwnerBusinessNum();
        final String ownerNm = ownerData.getOwnerNm();
        final String ownerCompanyName = ownerData.getOwnerCompanyName();
        final String ownerAddr = ownerData.getOwnerAddr();
        final String ownerTel = ownerData.getOwnerTel();

        return OwnerResponseDTO.builder()
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .ownerBusinessNum(ownerBusinessNum)
                .ownerNm(ownerNm)
                .ownerCompanyName(ownerCompanyName)
                .ownerAddr(ownerAddr)
                .ownerTel(ownerTel)
                .build();
    }

    @Override
    @Transactional
    public void ownerDataUpdate(OwnerUpdateDTO ownerUpdateData) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer ownerId = ownerUpdateData.getOwnerId();
        final String ownerPassword = ownerUpdateData.getOwnerPassword();
        final String ownerEmail = ownerUpdateData.getOwnerEmail();
        final String ownerNm = ownerUpdateData.getOwnerNm();
        final String ownerTel = ownerUpdateData.getOwnerTel();
        final String ownerCompanyName = ownerUpdateData.getOwnerCompanyName();
        final String ownerAddr = ownerUpdateData.getOwnerAddr();

        if (currentOwnerId == null || !currentOwnerId.equals(ownerId)) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(ownerId);
        if (existingOwner == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        final String existingOwnerPassword = existingOwner.getOwnerPassword();
        if (StringUtils.isBlank(ownerPassword) || !passwordEncoder.matches(ownerPassword, existingOwnerPassword)) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        Owner updateOwner = Owner.builder()
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .ownerNm(ownerNm)
                .ownerTel(ownerTel)
                .ownerCompanyName(ownerCompanyName)
                .ownerAddr(ownerAddr)
                .build();

        ownerRepository.ownerUpdate(updateOwner);
    }

    @Override
    @Transactional
    public void ownerDataDelete(OwnerDeleteDTO ownerDeleteData) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer ownerId = ownerDeleteData.getOwnerId();
        final String ownerPassword = ownerDeleteData.getOwnerPassword();

        if (currentOwnerId == null || !currentOwnerId.equals(ownerId)) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(ownerId);
        if (existingOwner == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        final String existingOwnerPassword = existingOwner.getOwnerPassword();
        if (StringUtils.isBlank(ownerPassword) || !passwordEncoder.matches(ownerPassword, existingOwnerPassword)) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
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