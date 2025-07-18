package com.acorn.api.controller.contact;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.contact.request.ContactUpdateReqDTO;
import com.acorn.api.service.contact.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactUpdateController {

    private final ContactService contactService;

    @PatchMapping("/api/contact/update/{contactId}")
    public ResponseEntity<ApiSuccessResponse<Object>> contactUpdate(@Valid ContactUpdateReqDTO updateData) {
        contactService.contactDataUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTACT_UPDATE_SUCCESS);
    }
}