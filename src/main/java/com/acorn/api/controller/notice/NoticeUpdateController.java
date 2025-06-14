package com.acorn.api.controller.notice;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.notice.NoticeUpdateDTO;
import com.acorn.api.service.notice.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeUpdateController {

    private final NoticeService noticeService;

    @PatchMapping("/api/admin/notice/update/{noticeId}")
    public ResponseEntity<ApiSuccessResponse<Object>> noticeUpdate(@Valid NoticeUpdateDTO updateData) {
        log.info(" *****************************    Notice Update START    *****************************");

        noticeService.noticeDataUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.NOTICE_UPDATE_SUCCESS);
    }
}