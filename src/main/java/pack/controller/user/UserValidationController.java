package pack.controller.user;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pack.api.code.common.ApiErrorCode;
import pack.api.code.common.ApiSuccessCode;
import pack.api.response.ApiErrorResponse;
import pack.api.response.ApiSuccessResponse;
import pack.service.user.UserService;

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
                    .errorStatus(ApiErrorCode.EMAIL_DUPLICATED.getErrorStatus())
                    .errorDivisionCode(ApiErrorCode.EMAIL_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiErrorCode.EMAIL_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiErrorCode.EMAIL_DUPLICATED.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiSuccessCode.EMAIL_AVAILABLE.getStatus())
                    .resultMsg(ApiSuccessCode.EMAIL_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiSuccessCode.EMAIL_AVAILABLE.getStatus()).body(emailCheckResponse);
        }
    }

    @GetMapping("/api/auth/user/userTelCheck")
    public ResponseEntity<Object> telPhoneCheck(@RequestParam String userTel) {
        log.info(" *****************************    telPhoneCheck START    *****************************");

        boolean userTelDuplicate = userService.isTelPhoneDuplicate(userTel);

        if (userTelDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorStatus())
                    .errorDivisionCode(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> telPhoneCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiSuccessCode.TELEPHONE_AVAILABLE.getStatus())
                    .resultMsg(ApiSuccessCode.TELEPHONE_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiSuccessCode.TELEPHONE_AVAILABLE.getStatus()).body(telPhoneCheckResponse);
        }
    }
}
