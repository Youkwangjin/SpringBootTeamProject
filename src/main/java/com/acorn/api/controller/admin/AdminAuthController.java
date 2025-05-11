package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.AdminLoginDTO;
import com.acorn.api.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @PostMapping("/api/admin/password/confirm")
    public ResponseEntity<ApiSuccessResponse<Object>> confirmPassword(@RequestBody AdminLoginDTO requestData) {
        log.info(" ************** [AdminAuth] Confirm Password started **************");

        adminService.verifyAdminPassword(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.ADMIN_DELETE_SUCCESS);
    }
}