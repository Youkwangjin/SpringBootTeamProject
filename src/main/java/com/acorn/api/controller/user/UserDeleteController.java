package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.user.UserDeleteDTO;
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

    @PostMapping("/api/user/delete/{userUUId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userDelete(@Valid @RequestBody UserDeleteDTO userDeleteData) {

        log.info(" *****************************    User Delete START    *****************************");

        userService.userDataDelete(userDeleteData);

        return ApiResponseBuilder.success(ApiHttpSuccessCode.INFO_DELETE_SUCCESS);
    }
}