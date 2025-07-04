package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.owner.request.OwnerDeleteReqDTO;
import com.acorn.api.service.owner.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerDeleteController {

    private final OwnerService ownerService;

    @PostMapping("/api/owner/delete/{ownerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> ownerDelete(@Valid @RequestBody OwnerDeleteReqDTO ownerDeleteData) {
        ownerService.ownerDataDelete(ownerDeleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_DELETE_SUCCESS);
    }
}