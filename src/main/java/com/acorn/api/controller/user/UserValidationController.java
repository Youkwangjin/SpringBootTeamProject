package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiValidationSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserValidationController {

    private final UserService userService;

    @GetMapping("/api/auth/user/emailCheck")
    public ResponseEntity<ApiSuccessResponse<Object>> emailCheck(@RequestParam String userEmail) {
        userService.isEmailDuplicate(userEmail);

        return ApiResponseBuilder.success(ApiValidationSuccessCode.EMAIL_AVAILABLE);
    }

    @GetMapping("/api/auth/user/userTelCheck")
    public ResponseEntity<ApiSuccessResponse<Object>> telPhoneCheck(@RequestParam String userTel) {
        userService.isTelPhoneDuplicate(userTel);

        return ApiResponseBuilder.success(ApiValidationSuccessCode.TELEPHONE_AVAILABLE);
    }
}