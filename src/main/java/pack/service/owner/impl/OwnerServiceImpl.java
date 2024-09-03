package pack.service.owner.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.model.owner.Owner;
import pack.repository.owner.OwnerRepository;
import pack.role.OwnerRole;
import pack.service.owner.OwnerService;
import pack.utils.OwnerSecurityUtil;

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
}
