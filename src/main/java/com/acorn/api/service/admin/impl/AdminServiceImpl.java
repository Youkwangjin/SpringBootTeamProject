package com.acorn.api.service.admin.impl;

import com.acorn.api.code.admin.ApiAdminErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.admin.AdminLoginDTO;
import com.acorn.api.dto.admin.AdminResponseDTO;
import com.acorn.api.entity.admin.Admin;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.admin.AdminRepository;
import com.acorn.api.service.admin.AdminService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AdminResponseDTO getAdminData() {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiAdminErrorCode.ADMIN_FOUND_ERROR);
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

    @Override
    public void verifyAdminPassword(AdminLoginDTO requestData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final String adminPassword = requestData.getAdminPassword();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Admin adminData = adminRepository.selectAdminById(currentAdminId);
        if (adminData == null) {
            throw new AcontainerException(ApiAdminErrorCode.ADMIN_FOUND_ERROR);
        }

        final String currentAdminPassword = adminData.getAdminPassword();
        if (StringUtils.isBlank(adminPassword) || !passwordEncoder.matches(adminPassword, currentAdminPassword)) {
            throw new AcontainerException(ApiAdminErrorCode.ADMIN_PASSWORD_ERROR);
        }
    }
}