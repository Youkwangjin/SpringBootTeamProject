package com.acorn.api.controller.contact;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.contact.request.ContactDeleteReqDTO;
import com.acorn.api.service.contact.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactDeleteController {

    private final ContactService contactService;

    @PostMapping("/api/contact/delete/{contactId}")
    public ResponseEntity<ApiSuccessResponse<Object>> contactDelete(@Valid @RequestBody ContactDeleteReqDTO deleteData) {
        contactService.contactDataDelete(deleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTACT_DELETE_SUCCESS);
    }
}