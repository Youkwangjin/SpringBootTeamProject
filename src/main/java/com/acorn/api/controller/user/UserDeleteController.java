package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.user.request.UserDeleteReqDTO;
import com.acorn.api.service.user.UserService;
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
public class UserDeleteController {

    private final UserService userService;

    @PostMapping("/api/user/delete/{userId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userDelete(@Valid @RequestBody UserDeleteReqDTO userDeleteData) {
        log.info(" *****************************    User Delete START    *****************************");

        userService.userDataDelete(userDeleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_DELETE_SUCCESS);
    }
}