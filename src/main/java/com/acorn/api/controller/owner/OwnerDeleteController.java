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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerDeleteController {

    private static final Logger log = LoggerFactory.getLogger(OwnerDeleteController.class);
    private final OwnerService ownerService;

    @DeleteMapping("/api/owner/delete/{userUUId}")
    public ResponseEntity<ApiSuccessResponse<Object>> ownerDelete(@Valid @RequestBody Owner owner) {

        log.info(" *****************************    User Delete START    *****************************");

        ownerService.ownerDataDelete(owner);

        ApiSuccessResponse<Object> deleteResponse = ApiSuccessResponse.builder()
                .resultCode(ApiHttpSuccessCode.INFO_DELETE_SUCCESS.getStatus())
                .resultMsg(ApiHttpSuccessCode.INFO_DELETE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiHttpSuccessCode.INFO_DELETE_SUCCESS.getStatus()).body(deleteResponse);

    }
}
