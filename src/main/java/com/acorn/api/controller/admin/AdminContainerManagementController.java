package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.ContainerManagementRequestDTO;
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
public class AdminContainerManagementController {

    private final AdminService adminService;

    @PostMapping("/api/admin/container/reviewRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> reviewRequest(@RequestBody ContainerManagementRequestDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Review request started **************");

        adminService.processReviewRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }

    @PostMapping("/api/admin/container/approvalRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> approvalRequest(@RequestBody ContainerManagementRequestDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Approval request started **************");

        adminService.processApprovalRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_APPROVAL_SUCCESS);
    }
}