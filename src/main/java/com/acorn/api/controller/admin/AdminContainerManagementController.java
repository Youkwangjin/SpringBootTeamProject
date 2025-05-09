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

    @PostMapping("/api/admin/container/rejectRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> rejectRequest(@RequestBody ContainerManagementRequestDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Reject request started **************");

        adminService.processRejectRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REJECT_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelApproval/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelApproval(@RequestBody ContainerManagementRequestDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Approval Cancel request started **************");

        adminService.processCancelApproval(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelReject/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelReject(@RequestBody ContainerManagementRequestDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Reject Cancel request started **************");

        adminService.processCancelReject(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }
}