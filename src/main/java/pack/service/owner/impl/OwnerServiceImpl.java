package pack.service.owner.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pack.repository.owner.OwnerRepository;
import pack.service.owner.OwnerService;
import pack.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

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
        String currentUserTelData = SecurityUtil.getAuthenticatedTelNumber();
        if (currentUserTelData != null && StringUtils.equals(currentUserTelData, ownerTel)) {
            return false;
        }
        return ownerRepository.isTelDuplicate(ownerTel);
    }
}
