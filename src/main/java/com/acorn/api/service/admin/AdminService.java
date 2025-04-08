package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    AdminResponseDTO getAdminData();
}