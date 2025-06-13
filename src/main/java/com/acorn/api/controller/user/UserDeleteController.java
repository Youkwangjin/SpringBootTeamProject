package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.user.request.UserDeleteResDTO;
import com.acorn.api.service.user.UserService;
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
public class UserDeleteController {

    private static final Logger log = LoggerFactory.getLogger(UserDeleteController.class);
    private final UserService userService;

    @PostMapping("/api/user/delete/{userId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userDelete(@Valid @RequestBody UserDeleteResDTO userDeleteData) {

        log.info(" *****************************    User Delete START    *****************************");

        userService.userDataDelete(userDeleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_DELETE_SUCCESS);
    }
}