package com.acorn.api.service.owner.impl;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.dto.owner.OwnerDeleteDTO;
import com.acorn.api.dto.owner.OwnerRegisterDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.dto.owner.OwnerUpdateDTO;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.model.owner.Owner;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.role.OwnerRole;
import com.acorn.api.service.owner.OwnerService;
import com.acorn.api.utils.OwnerSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;

    @Override
    public void isOwnerEmailDuplicate(String ownerEmail) {
        int emailCount = ownerRepository.isEmailDuplicate(ownerEmail);

        if (emailCount > 0) {
            throw new AcontainerException(ApiValidationErrorCode.EMAIL_DUPLICATED);
        }
    }

    @Override
    public void isBusinessNumDuplicate(String ownerBusinessNum) {
        int businessNumCount = ownerRepository.isBusinessNumDuplicate(ownerBusinessNum);

        if (businessNumCount > 0) {
            throw new AcontainerException(ApiValidationErrorCode.COMPANY_NAME_ERROR);
        }
    }

    @Override
    public void isTelPhoneDuplicate(String ownerTel) {
        int telCount = ownerRepository.isTelDuplicate(ownerTel);

        if (telCount > 0) {
            throw new AcontainerException(ApiValidationErrorCode.TELEPHONE_DUPLICATED);
        }
    }

    @Override
    public void isCompanyNameDuplicate(String ownerCompanyName) {
        int companyNameCount = ownerRepository.isCompanyNameDuplicate(ownerCompanyName);

        if (companyNameCount > 0) {
            throw new AcontainerException(ApiValidationErrorCode.COMPANY_NAME_ERROR);
        }
    }

    @Override
    @Transactional
    public void ownerRegister(OwnerRegisterDTO ownerRegisterData) {
        String encodedPassword = passwordEncoder.encode(ownerRegisterData.getOwnerPassword());

        Owner newRegisterDataOwner = Owner.builder()
                .ownerUUId(UUID.randomUUID().toString())
                .ownerEmail(ownerRegisterData.getOwnerEmail())
                .ownerBusinessNum(ownerRegisterData.getOwnerBusinessNum())
                .ownerPassword(encodedPassword)
                .ownerName(ownerRegisterData.getOwnerName())
                .ownerCompanyName(ownerRegisterData.getOwnerCompanyName())
                .ownerAddr(ownerRegisterData.getOwnerAddr())
                .ownerTel(ownerRegisterData.getOwnerTel())
                .ownerRole(OwnerRole.OWNER)
                .build();

        ownerRepository.ownerRegister(newRegisterDataOwner);
    }

    @Override
    public OwnerResponseDTO getOwnerData() throws AuthenticationException {
        String ownerUUId = OwnerSecurityUtil.getAuthenticatedUUId();

        if (!StringUtils.isNotBlank(ownerUUId)) {
            throw new UsernameNotFoundException("Owner is not authenticated");
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(ownerUUId);

        if (ownerData == null) {
            throw new UsernameNotFoundException("User data not found");
        }

        return OwnerResponseDTO.builder()
                .ownerUUId(ownerData.getOwnerUUId())
                .ownerEmail(ownerData.getOwnerEmail())
                .ownerBusinessNum(ownerData.getOwnerBusinessNum())
                .ownerName(ownerData.getOwnerName())
                .ownerCompanyName(ownerData.getOwnerCompanyName())
                .ownerAddr(ownerData.getOwnerAddr())
                .ownerTel(ownerData.getOwnerTel())
                .build();
    }

    @Override
    @Transactional
    public void ownerDataUpdate(OwnerUpdateDTO ownerUpdateData) throws AuthenticationException {
        String authenticatedUUId = OwnerSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, ownerUpdateData.getOwnerUUId())) {
            throw new AccessDeniedException("Unauthorized to update owner data");
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(authenticatedUUId);
        if (existingOwner == null) {
            throw new UsernameNotFoundException("Owner not found with UUID: " + authenticatedUUId);
        }

        if (StringUtils.isNotBlank(ownerUpdateData.getOwnerPassword()) && !passwordEncoder.matches(ownerUpdateData.getOwnerPassword(), existingOwner.getOwnerPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        Owner updateOwner = Owner.builder()
                .ownerUUId(ownerUpdateData.getOwnerUUId())
                .ownerEmail(ownerUpdateData.getOwnerEmail())
                .ownerName(ownerUpdateData.getOwnerName())
                .ownerTel(ownerUpdateData.getOwnerTel())
                .ownerCompanyName(ownerUpdateData.getOwnerCompanyName())
                .ownerAddr(ownerUpdateData.getOwnerAddr())
                .build();

        ownerRepository.ownerUpdate(updateOwner);
    }

    @Override
    @Transactional
    public void ownerDataDelete(OwnerDeleteDTO ownerDeleteData) {
        String authenticatedUUId = OwnerSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, ownerDeleteData.getOwnerUUId())) {
            throw new AccessDeniedException("Unauthorized to update owner data");
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(authenticatedUUId);
        if (existingOwner == null) {
            throw new UsernameNotFoundException("Owner not found with UUID: " + authenticatedUUId);
        }

        if (StringUtils.isNotBlank(ownerDeleteData.getOwnerPassword()) && !passwordEncoder.matches(ownerDeleteData.getOwnerPassword(), existingOwner.getOwnerPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        Owner deleteOwner = Owner.builder()
                .ownerUUId(ownerDeleteData.getOwnerUUId())
                .build();

        ownerRepository.ownerDelete(deleteOwner);
    }
}