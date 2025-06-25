package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminFaqDeleteReqDTO;
import com.acorn.api.dto.admin.request.AdminFaqRegisterReqDTO;
import com.acorn.api.dto.admin.request.AdminFaqUpdateReqDTO;
import com.acorn.api.service.admin.AdminFaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminFaqManagementController {

    private final AdminFaqService adminFaqService;

    @PostMapping("/api/admin/faq/register")
    public ResponseEntity<ApiSuccessResponse<Object>> faqRegister(@Valid @RequestBody AdminFaqRegisterReqDTO registerData) {
        adminFaqService.faqRegister(registerData);

        return ApiResponseBuilder.success(ApiSuccessCode.FAQ_SAVE_SUCCESS);
    }

    @PatchMapping("/api/admin/faq/update/{faqId}")
    public ResponseEntity<ApiSuccessResponse<Object>> faqUpdate(@Valid @RequestBody AdminFaqUpdateReqDTO updateData) {
        adminFaqService.faqUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.FAQ_UPDATE_SUCCESS);
    }

    @PostMapping("/api/admin/faq/delete/{faqId}")
    public ResponseEntity<ApiSuccessResponse<Object>> faqDelete(@Valid @RequestBody AdminFaqDeleteReqDTO deleteData) {
        adminFaqService.faqDelete(deleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.FAQ_DELETE_SUCCESS);
    }
}