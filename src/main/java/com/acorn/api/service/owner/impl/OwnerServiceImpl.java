package com.acorn.api.service.owner.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.dto.owner.OwnerDeleteDTO;
import com.acorn.api.dto.owner.OwnerRegisterDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.dto.owner.OwnerUpdateDTO;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.role.OwnerRole;
import com.acorn.api.service.owner.OwnerService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;

    @Override
    public void isOwnerEmailDuplicate(String ownerEmail) {
        Boolean exists = ownerRepository.isEmailDuplicate(ownerEmail);
        if (exists) {
            throw new AcontainerException(ApiValidationErrorCode.EMAIL_DUPLICATED_CONFLICT);
        }
    }

    @Override
    public void isBusinessNumDuplicate(String ownerBusinessNum) {
        Boolean businessNumCount = ownerRepository.isBusinessNumDuplicate(ownerBusinessNum);
        if (businessNumCount) {
            throw new AcontainerException(ApiValidationErrorCode.COMPANY_NAME_ERROR);
        }
    }

    @Override
    public void isTelPhoneDuplicate(String ownerTel) {
        Boolean telCount = ownerRepository.isTelDuplicate(ownerTel);
        if (telCount) {
            throw new AcontainerException(ApiValidationErrorCode.TELPHONE_DUPLICATED_CONFLICT);
        }
    }

    @Override
    public void isCompanyNameDuplicate(String ownerCompanyName) {
        Boolean companyNameCount = ownerRepository.isCompanyNameDuplicate(ownerCompanyName);
        if (companyNameCount) {
            throw new AcontainerException(ApiValidationErrorCode.COMPANY_NAME_ERROR);
        }
    }

    @Override
    @Transactional
    public void ownerRegister(OwnerRegisterDTO ownerRegisterData) {
        final Integer ownerId = ownerRepository.selectOwnerIdKey();
        final String ownerEmail = ownerRegisterData.getOwnerEmail();
        final String encodedPassword = passwordEncoder.encode(ownerRegisterData.getOwnerPassword());
        final String ownerBusinessNum = ownerRegisterData.getOwnerBusinessNum();
        final String ownerNm = ownerRegisterData.getOwnerNm();
        final String ownerCompanyName = ownerRegisterData.getOwnerCompanyName();
        final String ownerAddr = ownerRegisterData.getOwnerAddr();
        final String ownerTel = ownerRegisterData.getOwnerTel();

        final Owner newRegisterDataOwner = Owner.builder()
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .ownerBusinessNum(ownerBusinessNum)
                .ownerPassword(encodedPassword)
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
        Integer ownerId = CommonSecurityUtil.getCurrentOwnerId();
        if (ownerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(ownerId);
        if (ownerData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }
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
        Integer ownerId = CommonSecurityUtil.getCurrentOwnerId();
        if (ownerId == null || !ownerId.equals(ownerUpdateData.getOwnerId())) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(ownerId);
        if (existingOwner == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        if (StringUtils.isNotBlank(ownerUpdateData.getOwnerPassword()) && !passwordEncoder.matches(ownerUpdateData.getOwnerPassword(), existingOwner.getOwnerPassword())) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }
        final String ownerEmail = ownerUpdateData.getOwnerEmail();
        final String ownerNm = ownerUpdateData.getOwnerNm();
        final String ownerTel = ownerUpdateData.getOwnerTel();
        final String ownerCompanyName = ownerUpdateData.getOwnerCompanyName();
        final String ownerAddr = ownerUpdateData.getOwnerAddr();

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
        Integer ownerId = CommonSecurityUtil.getCurrentOwnerId();
        if (ownerId == null || !ownerId.equals(ownerDeleteData.getOwnerId())) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(ownerId);
        if (existingOwner == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        if (StringUtils.isNotBlank(ownerDeleteData.getOwnerPassword()) && !passwordEncoder.matches(ownerDeleteData.getOwnerPassword(), existingOwner.getOwnerPassword())) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        Owner deleteOwner = Owner.builder()
                .ownerId(ownerId)
                .build();

        ownerRepository.ownerDelete(deleteOwner);
    }
}