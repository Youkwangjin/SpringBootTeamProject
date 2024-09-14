package com.acorn.api.controller.owner;

import com.acorn.api.com.acorn.api.code.code.common.ApiHttpSuccessCode;
import com.acorn.api.com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.model.owner.Owner;
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
    public ResponseEntity<ApiSuccessResponse<Object>> updateOwner(@Valid @RequestBody Owner owner) {

        log.info(" *****************************    Owner Update START    *****************************");

        ownerService.ownerDataUpdate(owner);

        ApiSuccessResponse<Object> updateResponse = ApiSuccessResponse.builder()
                .resultCode(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getStatus())
                .resultMsg(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getStatus()).body(updateResponse);
    }
}
