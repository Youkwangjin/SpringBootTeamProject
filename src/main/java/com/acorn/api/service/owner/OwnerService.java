package com.acorn.api.service.owner;

import com.acorn.api.model.owner.Owner;
import org.springframework.stereotype.Service;

@Service
public interface OwnerService {

    boolean isOwnerEmailDuplicate(String ownerEmail);

    boolean isBusinessNumDuplicate(String ownerBusinessNum);

    boolean isTelPhoneDuplicate(String ownerTel);

    boolean isCompanyNameDuplicate(String ownerCompanyName);

    void ownerRegister(Owner owner);

    Owner getOwnerData();

    void ownerDataUpdate(Owner owner);

    void ownerDataDelete(Owner owner);
}
