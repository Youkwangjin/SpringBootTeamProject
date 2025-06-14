package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminUserDeleteReqDTO;
import com.acorn.api.dto.admin.request.AdminUserUpdateReqDTO;
import com.acorn.api.service.admin.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminUserManagementController {

    private final AdminUserService adminUserService;

    @PatchMapping("/api/admin/user/update/{userId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userUpdate(@Valid @RequestBody AdminUserUpdateReqDTO requestData) {
        log.info(" ************** [AdminUserManagement] Update request started **************");

        adminUserService.adminUpdateUser(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_UPDATE_SUCCESS);
    }

    @PostMapping("/api/admin/user/delete/{userId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userDelete(@Valid @RequestBody AdminUserDeleteReqDTO requestData) {
        log.info(" ************** [AdminUserManagement] Delete request started **************");

        adminUserService.adminDeleteUser(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.ADMIN_DELETE_SUCCESS);
    }
}