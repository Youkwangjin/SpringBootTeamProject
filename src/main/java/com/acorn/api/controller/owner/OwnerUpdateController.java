package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.owner.OwnerUpdateDTO;
import com.acorn.api.service.owner.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerUpdateController {

    private static final Logger log = LoggerFactory.getLogger(OwnerUpdateController.class);
    private final OwnerService ownerService;

    @PatchMapping("/api/owner/update/{ownerUUId}")
    public ResponseEntity<ApiSuccessResponse<Object>> updateOwner(@Valid @RequestBody OwnerUpdateDTO ownerUpdateData) {

        log.info(" *****************************    Owner Update START    *****************************");

        ownerService.ownerDataUpdate(ownerUpdateData);

        ApiSuccessResponse<Object> updateResponse = ApiSuccessResponse.builder()
                .httpStatus(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getHttpStatus())
                .resultMsg(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getMessage())
                .build();

        return ResponseEntity.status(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getHttpStatus()).body(updateResponse);
    }
}