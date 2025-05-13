package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.AdminOwnerDeleteRequestDTO;
import com.acorn.api.dto.admin.AdminOwnerUpdateRequestDTO;
import com.acorn.api.service.admin.AdminOwnerService;
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
public class AdminOwnerManagementController {

    private final AdminOwnerService adminOwnerService;

    @PatchMapping("/api/admin/owner/update/{ownerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> updateOwner(@Valid @RequestBody AdminOwnerUpdateRequestDTO requestData) {
        log.info(" ************** [AdminOwnerManagement] Update request started **************");

        adminOwnerService.adminOwnerUpdate(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_UPDATE_SUCCESS);
    }

    @PostMapping("/api/admin/owner/delete/{ownerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> deleteOwner(@Valid @RequestBody AdminOwnerDeleteRequestDTO requestData) {
        log.info(" ************** [AdminOwnerManagement] Delete request started **************");

        adminOwnerService.adminOwnerDelete(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.ADMIN_DELETE_SUCCESS);
    }
}