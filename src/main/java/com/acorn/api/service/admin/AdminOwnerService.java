package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminOwnerDeleteRequestDTO;
import com.acorn.api.dto.admin.AdminOwnerListDTO;
import com.acorn.api.dto.admin.AdminOwnerUpdateRequestDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminOwnerService {

    List<AdminOwnerListDTO> getOwnerList(AdminOwnerListDTO listData);

    OwnerResDTO getOwnerData(Integer ownerId);

    void adminOwnerUpdate(@Valid AdminOwnerUpdateRequestDTO requestData);

    void adminOwnerDelete(@Valid AdminOwnerDeleteRequestDTO requestData);
}