package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.user.UserUpdateDTO;
import com.acorn.api.service.user.UserService;
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
public class UserUpdateController {

    private static final Logger log = LoggerFactory.getLogger(UserUpdateController.class);
    private final UserService userService;

    @PatchMapping("/api/user/update/{userId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userUpdate(@Valid @RequestBody UserUpdateDTO userUpdateData) {

        log.info(" *****************************    User Update START    *****************************");

        userService.userDataUpdate(userUpdateData);

        return ApiResponseBuilder.success(ApiSuccessCode.INFO_UPDATE_SUCCESS);
    }
}