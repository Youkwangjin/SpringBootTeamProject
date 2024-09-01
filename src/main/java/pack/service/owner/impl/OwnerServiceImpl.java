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
import pack.utils.UserSecurityUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;

    @Override
    public boolean isOwnerEmailDuplicate(String ownerEmail) {
        return ownerRepository.isEmailDuplicate(ownerEmail);
    }

    @Override
    public boolean isBusinessNumDuplicate(String ownerBusinessNum) {
        return ownerRepository.isBusinessNumDuplicate(ownerBusinessNum);
    }

    @Override
    public boolean isTelPhoneDuplicate(String ownerTel) {
        String currentUserTelData = UserSecurityUtil.getAuthenticatedTelNumber();
        if (currentUserTelData != null && StringUtils.equals(currentUserTelData, ownerTel)) {
            return false;
        }
        return ownerRepository.isTelDuplicate(ownerTel);
    }

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
