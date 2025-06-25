package com.acorn.api.controller.notice;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.notice.request.NoticeDeleteReqDTO;
import com.acorn.api.service.notice.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeDeleteController {

    private final NoticeService noticeService;

    @PostMapping("/api/admin/notice/delete/{noticeId}")
    public ResponseEntity<ApiSuccessResponse<Object>> noticeDelete(@Valid @RequestBody NoticeDeleteReqDTO deleteData) {
        noticeService.noticeDataDelete(deleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.NOTICE_DELETE_SUCCESS);
    }
}