package com.acorn.api.service.owner;

import com.acorn.api.dto.owner.OwnerRegisterDTO;
import com.acorn.api.model.owner.Owner;
import org.springframework.stereotype.Service;

@Service
public interface OwnerService {

    boolean isOwnerEmailDuplicate(String ownerEmail);

    boolean isBusinessNumDuplicate(String ownerBusinessNum);

    boolean isTelPhoneDuplicate(String ownerTel);

    boolean isCompanyNameDuplicate(String ownerCompanyName);

    void ownerRegister(OwnerRegisterDTO ownerRegisterData);

    Owner getOwnerData();

    void ownerDataUpdate(Owner owner);

    void ownerDataDelete(Owner owner);
}
