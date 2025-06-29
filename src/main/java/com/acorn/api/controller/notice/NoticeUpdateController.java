package com.acorn.api.controller.notice;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.notice.request.NoticeUpdateReqDTO;
import com.acorn.api.service.notice.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeUpdateController {

    private final NoticeService noticeService;

    @PatchMapping("/api/admin/notice/update/{noticeId}")
    public ResponseEntity<ApiSuccessResponse<Object>> noticeUpdate(@Valid NoticeUpdateReqDTO updateData) {
        noticeService.noticeDataUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.NOTICE_UPDATE_SUCCESS);
    }
}