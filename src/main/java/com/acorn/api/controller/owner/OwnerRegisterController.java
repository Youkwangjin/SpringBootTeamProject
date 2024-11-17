package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.owner.OwnerRegisterDTO;
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
public class OwnerRegisterController {

    private static final Logger log = LoggerFactory.getLogger(OwnerRegisterController.class);
    private final OwnerService ownerService;

    @PostMapping("/api/auth/owner/register")
    public ResponseEntity<ApiSuccessResponse<Object>> registerOwner(@Valid @RequestBody OwnerRegisterDTO ownerRegisterData) {

        log.info(" *****************************    Register START    *****************************");

        ownerService.ownerRegister(ownerRegisterData);

        ApiSuccessResponse<Object> registerResponse = ApiSuccessResponse.builder()
                .httpStatus(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getHttpStatus())
                .resultMsg(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getMessage())
                .build();

        return ResponseEntity.status(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getHttpStatus()).body(registerResponse);
    }
}