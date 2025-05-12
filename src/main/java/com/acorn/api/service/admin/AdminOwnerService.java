package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminOwnerListDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminOwnerService {

    List<AdminOwnerListDTO> getOwnerList(AdminOwnerListDTO listData);

    OwnerResponseDTO getOwnerData(Integer ownerId);
}