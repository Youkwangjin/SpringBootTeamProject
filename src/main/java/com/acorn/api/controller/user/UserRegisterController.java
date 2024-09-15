package com.acorn.api.controller.user;


import com.acorn.api.code.common.ApiHttpSuccessCode;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.user.UserRegisterDTO;
import com.acorn.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterController.class);
    private final UserService userService;

    @PostMapping("/api/auth/user/register")
    public ResponseEntity<ApiSuccessResponse<Object>> userRegister(@Valid @RequestBody UserRegisterDTO userRegisterData) {

        log.info(" *****************************    Register START    *****************************");

        userService.userRegister(userRegisterData);

        ApiSuccessResponse<Object> registerResponse = ApiSuccessResponse.builder()
                .resultCode(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getStatus())
                .resultMsg(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getStatus()).body(registerResponse);
    }
}
