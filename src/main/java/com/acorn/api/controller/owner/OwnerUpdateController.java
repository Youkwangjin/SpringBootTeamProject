package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.owner.request.OwnerUpdateReqDTO;
import com.acorn.api.service.owner.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerUpdateController {

    private final OwnerService ownerService;

    @PatchMapping("/api/owner/update/{ownerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> updateOwner(@Valid @RequestBody OwnerUpdateReqDTO ownerUpdateData) {
        ownerService.ownerDataUpdate(ownerUpdateData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_UPDATE_SUCCESS);
    }
}