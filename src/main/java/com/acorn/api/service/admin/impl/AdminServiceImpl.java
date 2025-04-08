package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.admin.AdminResponseDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.service.admin.AdminService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public AdminResponseDTO getAdminData() {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Admin adminData = adminRepository.selectAllAdminData(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiErrorCode.USER_FOUND_ERROR);
        }

        final Integer adminId = adminData.getAdminId();
        final String adminEmail = adminData.getAdminEmail();
        final String adminPassword = adminData.getAdminPassword();
        final String adminNm = adminData.getAdminNm();

        return AdminResponseDTO.builder()
                .adminId(adminId)
                .adminEmail(adminEmail)
                .adminPassword(adminPassword)
                .adminNm(adminNm)
                .build();
    }
}