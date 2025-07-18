package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminOwnerDeleteReqDTO;
import com.acorn.api.dto.admin.request.AdminOwnerUpdateReqDTO;
import com.acorn.api.service.admin.AdminOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminOwnerManagementController {

    private final AdminOwnerService adminOwnerService;

    @PatchMapping("/api/admin/owner/update/{ownerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> updateOwner(@Valid @RequestBody AdminOwnerUpdateReqDTO requestData) {
        adminOwnerService.adminOwnerUpdate(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_UPDATE_SUCCESS);
    }

    @PostMapping("/api/admin/owner/delete/{ownerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> deleteOwner(@Valid @RequestBody AdminOwnerDeleteReqDTO requestData) {
        adminOwnerService.adminOwnerDelete(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.ADMIN_DELETE_SUCCESS);
    }
}