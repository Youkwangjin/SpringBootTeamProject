package com.acorn.api.controller.contact;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.contact.request.ContactCancelReqDTO;
import com.acorn.api.service.contact.ContactService;
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
public class ContactCancelController {

    private final ContactService contactService;

    @PatchMapping("/api/contact/cancel/{contactId}")
    public ResponseEntity<ApiSuccessResponse<Object>> contactCancel(@Valid @RequestBody ContactCancelReqDTO cancelData) {
        log.info(" *****************************    Contact Cancel START    *****************************");

        contactService.contactDataCancel(cancelData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTACT_CANCEL_SUCCESS);
    }
}