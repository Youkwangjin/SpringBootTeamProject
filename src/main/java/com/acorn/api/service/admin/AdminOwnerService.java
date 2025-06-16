package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.request.AdminOwnerDeleteReqDTO;
import com.acorn.api.dto.admin.request.AdminOwnerUpdateReqDTO;
import com.acorn.api.dto.admin.response.AdminOwnerListResDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminOwnerService {

    List<AdminOwnerListResDTO> getOwnerList(CommonListReqDTO listData);

    OwnerResDTO getOwnerData(Integer ownerId);

    void adminOwnerUpdate(@Valid AdminOwnerUpdateReqDTO requestData);

    void adminOwnerDelete(@Valid AdminOwnerDeleteReqDTO requestData);
}