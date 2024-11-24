package com.acorn.api.service.owner;

import com.acorn.api.dto.owner.OwnerDeleteDTO;
import com.acorn.api.dto.owner.OwnerRegisterDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.dto.owner.OwnerUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface OwnerService {

    void isOwnerEmailDuplicate(String ownerEmail);

    void isBusinessNumDuplicate(String ownerBusinessNum);

    void isTelPhoneDuplicate(String ownerTel);

    void isCompanyNameDuplicate(String ownerCompanyName);

    void ownerRegister(OwnerRegisterDTO ownerRegisterData);

    OwnerResponseDTO getOwnerData();

    void ownerDataUpdate(OwnerUpdateDTO ownerUpdateData);

    void ownerDataDelete(OwnerDeleteDTO ownerDeleteData);
}