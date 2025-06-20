package com.acorn.api.controller.admin;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.admin.request.AdminContactReviewReqDTO;
import com.acorn.api.dto.admin.request.AdminContactAnswerReqDTO;
import com.acorn.api.dto.contact.response.ContactFileDownloadResDTO;
import com.acorn.api.service.admin.AdminContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminContactManagementController {

    private final AdminContactService adminContactService;

    @GetMapping("/api/admin/contact/file/download/{contactId}/{contactFileId}")
    public ResponseEntity<byte[]> contactFileDownload(@PathVariable Integer contactId, @PathVariable Integer contactFileId) {
        log.info(" *****************************  [AdminContactManagement]  Contact File  Download START    *****************************");

        ContactFileDownloadResDTO resData = adminContactService.contactAdminFileDownload(contactId, contactFileId);

        String fileName = UriUtils.encode(resData.getOriginalFileName(), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resData.getFileBytes(), headers, HttpStatus.OK);
    }

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