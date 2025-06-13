package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.user.request.UserRegisterReqDTO;
import com.acorn.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    @PostMapping("/api/auth/user/register")
    public ResponseEntity<ApiSuccessResponse<Object>> userRegister(@Valid @RequestBody UserRegisterReqDTO userRegisterData) {
        log.info(" *****************************    Register START    *****************************");

        userService.userRegister(userRegisterData);

        return ApiResponseBuilder.success(ApiSuccessCode.REGISTER_SUCCESS);
    }
}