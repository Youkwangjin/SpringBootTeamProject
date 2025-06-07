package com.acorn.api.controller.notice;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.notice.NoticeDeleteDTO;
import com.acorn.api.service.notice.NoticeService;
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
public class NoticeDeleteController {

    private final NoticeService noticeService;

    @PostMapping("/api/admin/notice/delete/{noticeId}")
    public ResponseEntity<ApiSuccessResponse<Object>> noticeDelete(@Valid @RequestBody NoticeDeleteDTO deleteData) {
        log.info(" *****************************    Notice Delete START    *****************************");

        noticeService.noticeDataDelete(deleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.NOTICE_DELETE_SUCCESS);
    }
}