package com.acorn.api.service.owner;

import com.acorn.api.dto.owner.request.OwnerDeleteReqDTO;
import com.acorn.api.dto.owner.request.OwnerRegisterReqDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
import com.acorn.api.dto.owner.request.OwnerUpdateReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface OwnerService {

    void isOwnerEmailDuplicate(String ownerEmail);

    void isBusinessNumDuplicate(String ownerBusinessNum);

    void isTelPhoneDuplicate(String ownerTel);

    void isCompanyNameDuplicate(String ownerCompanyName);

    void ownerRegister(OwnerRegisterReqDTO ownerRegisterData);

    OwnerResDTO getOwnerData();

    void ownerDataUpdate(OwnerUpdateReqDTO ownerUpdateData);

    void ownerDataDelete(OwnerDeleteReqDTO ownerDeleteData);
}