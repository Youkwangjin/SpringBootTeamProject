package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminLoginReqDTO;
import com.acorn.api.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @PostMapping("/api/admin/password/confirm")
    public ResponseEntity<ApiSuccessResponse<Object>> confirmPassword(@RequestBody AdminLoginReqDTO requestData) {
        adminService.verifyAdminPassword(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.ADMIN_PASSWORD_SUCCESS);
    }
}