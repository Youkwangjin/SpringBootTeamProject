package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.request.AdminLoginReqDTO;
import com.acorn.api.dto.admin.response.AdminResDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    AdminResDTO getAdminData();

    void verifyAdminPassword(AdminLoginReqDTO requestData);
}