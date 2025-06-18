package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminContactReviewReqDTO;
import com.acorn.api.dto.admin.request.AdminContactAnswerReqDTO;
import com.acorn.api.service.admin.AdminContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminContactManagementController {

    private final AdminContactService adminContactService;

    @PatchMapping("/api/admin/contact/reviewRequest/{contactId}")
    public ResponseEntity<ApiSuccessResponse<Object>> reviewRequest(@Valid @RequestBody AdminContactReviewReqDTO requsetData) {
        log.info(" ************** [AdminContactManagement] Review request started **************");

        adminContactService.processReviewRequest(requsetData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTACT_REVIEW_SUCCESS);
    }

    @PatchMapping("/api/admin/contact/answerRequest/{contactId}")
    public ResponseEntity<ApiSuccessResponse<Object>> answerRequest(@Valid @RequestBody AdminContactAnswerReqDTO requsetData) {
        log.info(" ************** [AdminContactManagement] Answer request started **************");

        adminContactService.processAnswerRequest(requsetData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTACT_ANSWER_SUCCESS);
    }
}