package com.acorn.api.controller.user;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.common.ApiValidationSuccessCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserValidationController {

    private static final Logger log = LoggerFactory.getLogger(UserValidationController.class);
    private final UserService userService;

    @GetMapping("/api/auth/user/emailCheck")
    public ResponseEntity<Object> emailCheck(@RequestParam String userEmail) {
        log.info(" *****************************    emailCheck START    *****************************");

        boolean emailDuplicate = userService.isEmailDuplicate(userEmail);

        if (emailDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .httpStatus(ApiValidationErrorCode.EMAIL_DUPLICATED.getHttpStatus())
                    .errorDivisionCode(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorMsg())
                    .build();

            return ResponseEntity.status(ApiValidationErrorCode.EMAIL_DUPLICATED.getHttpStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .httpStatus(ApiValidationSuccessCode.EMAIL_AVAILABLE.getHttpStatus())
                    .resultMsg(ApiValidationSuccessCode.EMAIL_AVAILABLE.getMessage())
                    .build();

            return ResponseEntity.status(ApiValidationSuccessCode.EMAIL_AVAILABLE.getHttpStatus()).body(emailCheckResponse);
        }
    }

    @GetMapping("/api/auth/user/userTelCheck")
    public ResponseEntity<Object> telPhoneCheck(@RequestParam String userTel) {
        log.info(" *****************************    telPhoneCheck START    *****************************");

        boolean userTelDuplicate = userService.isTelPhoneDuplicate(userTel);

        if (userTelDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .httpStatus(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getHttpStatus())
                    .errorDivisionCode(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorMsg())
                    .build();

            return ResponseEntity.status(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getHttpStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> telPhoneCheckResponse = ApiSuccessResponse.builder()
                    .httpStatus(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getHttpStatus())
                    .resultMsg(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getMessage())
                    .build();

            return ResponseEntity.status(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getHttpStatus()).body(telPhoneCheckResponse);
        }
    }
}