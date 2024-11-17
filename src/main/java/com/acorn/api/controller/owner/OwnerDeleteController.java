package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.owner.OwnerDeleteDTO;
import com.acorn.api.service.owner.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerDeleteController {

    private static final Logger log = LoggerFactory.getLogger(OwnerDeleteController.class);
    private final OwnerService ownerService;

    @PostMapping("/api/owner/delete/{ownerUUId}")
    public ResponseEntity<ApiSuccessResponse<Object>> ownerDelete(@Valid @RequestBody OwnerDeleteDTO ownerDeleteData) {

        log.info(" *****************************    User Delete START    *****************************");

        ownerService.ownerDataDelete(ownerDeleteData);

        ApiSuccessResponse<Object> deleteResponse = ApiSuccessResponse.builder()
                .httpStatus(ApiHttpSuccessCode.INFO_DELETE_SUCCESS.getHttpStatus())
                .resultMsg(ApiHttpSuccessCode.INFO_DELETE_SUCCESS.getMessage())
                .build();

        return ResponseEntity.status(ApiHttpSuccessCode.INFO_DELETE_SUCCESS.getHttpStatus()).body(deleteResponse);
    }
}