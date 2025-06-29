package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminContainerManagementReqDTO;
import com.acorn.api.service.admin.AdminContainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminContainerManagementController {

    private final AdminContainerService adminContainerService;

    @PostMapping("/api/admin/container/reviewRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> reviewRequest(@Valid @RequestBody AdminContainerManagementReqDTO requestData) {
        adminContainerService.processReviewRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }

    @PostMapping("/api/admin/container/approvalRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> approvalRequest(@Valid @RequestBody AdminContainerManagementReqDTO requestData) {
        adminContainerService.processApprovalRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_APPROVAL_SUCCESS);
    }

    @PostMapping("/api/admin/container/rejectRequest/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> rejectRequest(@Valid @RequestBody AdminContainerManagementReqDTO requestData) {
        adminContainerService.processRejectRequest(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REJECT_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelReview/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelReview(@Valid @RequestBody AdminContainerManagementReqDTO requestData) {
        adminContainerService.processCancelReview(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_PENDING_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelApproval/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelApproval(@Valid @RequestBody AdminContainerManagementReqDTO requestData) {
        adminContainerService.processCancelApproval(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }

    @PostMapping("/api/admin/container/cancelReject/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> cancelReject(@Valid @RequestBody AdminContainerManagementReqDTO requestData) {
        adminContainerService.processCancelReject(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REVIEW_SUCCESS);
    }
}