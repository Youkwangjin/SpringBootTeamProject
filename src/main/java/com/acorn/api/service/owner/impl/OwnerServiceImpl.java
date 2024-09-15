package com.acorn.api.service.owner.impl;

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

    // 이메일 중복 검증
    @Override
    public boolean isOwnerEmailDuplicate(String ownerEmail) {
        String currentOwnerEmailData = OwnerSecurityUtil.getAuthenticatedEmail();
        if (currentOwnerEmailData != null && StringUtils.equals(currentOwnerEmailData, ownerEmail)) {
            return false;
        }

        return ownerRepository.isEmailDuplicate(ownerEmail);
    }
    
    // 사업자 번호 중복 검증
    @Override
    public boolean isBusinessNumDuplicate(String ownerBusinessNum) {
        return ownerRepository.isBusinessNumDuplicate(ownerBusinessNum);
    }
    
    // 전화번호 중복 검증
    @Override
    public boolean isTelPhoneDuplicate(String ownerTel) {
        String currentOwnerTelData = OwnerSecurityUtil.getAuthenticatedTelNumber();
        if (currentOwnerTelData != null && StringUtils.equals(currentOwnerTelData, ownerTel)) {
            return false;
        }

        return ownerRepository.isTelDuplicate(ownerTel);
    }
    
    // 회사명 중복 검증
    @Override
    public boolean isCompanyNameDuplicate(String ownerCompanyName) {
        String currentOwnerCompanyNameData = OwnerSecurityUtil.getAuthenticatedCompanyName();
        if (currentOwnerCompanyNameData != null && StringUtils.equals(currentOwnerCompanyNameData, ownerCompanyName)) {
            return false;
        }

        return ownerRepository.isCompanyNameDuplicate(ownerCompanyName);
    }
    
    // 공급자 회원가입
    @Override
    @Transactional
    public void ownerRegister(Owner owner) {
        String encodedPassword = passwordEncoder.encode(owner.getOwnerPassword());

        Owner newOwner = Owner.builder()
                .ownerUUId(UUID.randomUUID().toString())
                .ownerEmail(owner.getOwnerEmail())
                .ownerBusinessNum(owner.getOwnerBusinessNum())
                .ownerPassword(encodedPassword)
                .ownerName(owner.getOwnerName())
                .ownerCompanyName(owner.getOwnerCompanyName())
                .ownerAddress(owner.getOwnerAddress())
                .ownerTel(owner.getOwnerTel())
                .ownerRole(OwnerRole.OWNER)
                .build();

        ownerRepository.ownerRegister(newOwner);

    }
    
    // 공급자 정보 불러오기
    @Override
    public Owner getOwnerData() throws AuthenticationException {
        String ownerUUId = OwnerSecurityUtil.getAuthenticatedUUId();

        if (!StringUtils.isNotBlank(ownerUUId)) {
            throw new UsernameNotFoundException("Owner is not authenticated");
        }

        Owner ownerData = ownerRepository.selectAllOwnerData(ownerUUId);

        if (ownerData == null) {
            throw new UsernameNotFoundException("User data not found");
        }

        return ownerData;
    }

    // 공급자 회원수정
    @Override
    @Transactional
    public void ownerDataUpdate(Owner owner) throws AuthenticationException {
        String authenticatedUUId = OwnerSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, owner.getOwnerUUId())) {
            throw new AccessDeniedException("Unauthorized to update owner data");
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(authenticatedUUId);
        if (existingOwner == null) {
            throw new UsernameNotFoundException("Owner not found with UUID: " + authenticatedUUId);
        }

        if (StringUtils.isNotBlank(owner.getOwnerPassword()) && !passwordEncoder.matches(owner.getOwnerPassword(), existingOwner.getOwnerPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        Owner updateOwner = Owner.builder()
                .ownerUUId(owner.getOwnerUUId())
                .ownerEmail(owner.getOwnerEmail())
                .ownerName(owner.getOwnerName())
                .ownerTel(owner.getOwnerTel())
                .ownerCompanyName(owner.getOwnerCompanyName())
                .ownerAddress(owner.getOwnerAddress())
                .build();

        ownerRepository.ownerUpdate(updateOwner);

    }

    // 회원탈퇴
    @Override
    @Transactional
    public void ownerDataDelete(Owner owner) {
        String authenticatedUUId = OwnerSecurityUtil.getAuthenticatedUUId();

        if (StringUtils.isBlank(authenticatedUUId) || !StringUtils.equals(authenticatedUUId, owner.getOwnerUUId())) {
            throw new AccessDeniedException("Unauthorized to update owner data");
        }

        Owner existingOwner = ownerRepository.selectAllOwnerData(authenticatedUUId);
        if (existingOwner == null) {
            throw new UsernameNotFoundException("Owner not found with UUID: " + authenticatedUUId);
        }

        if (StringUtils.isNotBlank(owner.getOwnerPassword()) && !passwordEncoder.matches(owner.getOwnerPassword(), existingOwner.getOwnerPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        ownerRepository.ownerDelete(owner);
    }
}
