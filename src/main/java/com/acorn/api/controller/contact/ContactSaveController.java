package com.acorn.api.controller.contact;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.contact.requset.ContactSaveReqDTO;
import com.acorn.api.service.contact.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactSaveController {

    private final ContactService contactService;

    @PostMapping("/api/contact/save")
    public ResponseEntity<ApiSuccessResponse<Object>> contactSave(@Valid ContactSaveReqDTO saveData) {
        log.info(" *****************************    Contact Save START    *****************************");

        contactService.contactSaveData(saveData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTACT_SAVE_SUCCESS);
    }
}