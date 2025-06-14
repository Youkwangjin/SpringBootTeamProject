package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.owner.request.OwnerRegisterReqDTO;
import com.acorn.api.service.owner.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OwnerRegisterController {

    private final OwnerService ownerService;

    @PostMapping("/api/auth/owner/register")
    public ResponseEntity<ApiSuccessResponse<Object>> registerOwner(@Valid @RequestBody OwnerRegisterReqDTO ownerRegisterData) {
        log.info(" *****************************    Register START    *****************************");

        ownerService.ownerRegister(ownerRegisterData);

        return ApiResponseBuilder.success(ApiSuccessCode.REGISTER_SUCCESS);
    }
}