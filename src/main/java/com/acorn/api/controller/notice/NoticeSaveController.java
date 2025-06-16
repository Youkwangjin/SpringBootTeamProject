package com.acorn.api.controller.notice;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.notice.request.NoticeSaveReqDTO;
import com.acorn.api.service.notice.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeSaveController {

    private final NoticeService noticeService;

    @PostMapping("/api/admin/notice/save")
    public ResponseEntity<ApiSuccessResponse<Object>> noticeSave(@Valid NoticeSaveReqDTO saveData) {
        log.info(" *****************************    Notice Save START    *****************************");

        noticeService.noticeDataSave(saveData);

        return ApiResponseBuilder.success(ApiSuccessCode.NOTICE_SAVE_SUCCESS);
    }
}