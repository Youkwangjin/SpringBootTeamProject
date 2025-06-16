package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.ContainerManagementReqDTO;
import com.acorn.api.service.admin.AdminContainerService;
import jakarta.validation.Valid;
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

    private final AdminContainerService adminContainerService;

    @PostMapping("/api/admin/container/reviewRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> reviewRequest(@Valid @RequestBody ContainerManagementReqDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Review request started **************");

        adminContainerService.processReviewRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }

    @PostMapping("/api/admin/container/approvalRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> approvalRequest(@Valid @RequestBody ContainerManagementReqDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Approval request started **************");

        adminContainerService.processApprovalRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_APPROVAL_SUCCESS);
    }

    @PostMapping("/api/admin/container/rejectRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> rejectRequest(@Valid @RequestBody ContainerManagementReqDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Reject request started **************");

        adminContainerService.processRejectRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REJECT_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelReview/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelReview(@Valid @RequestBody ContainerManagementReqDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Review Cancel request started **************");

        adminContainerService.processCancelReview(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_PENDING_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelApproval/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelApproval(@Valid @RequestBody ContainerManagementReqDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Approval Cancel request started **************");

        adminContainerService.processCancelApproval(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelReject/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelReject(@Valid @RequestBody ContainerManagementReqDTO requestData) {
        log.info(" ************** [AdminContainerManagement] Reject Cancel request started **************");

        adminContainerService.processCancelReject(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }
}