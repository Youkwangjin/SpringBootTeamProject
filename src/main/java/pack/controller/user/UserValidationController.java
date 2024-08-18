package pack.controller.user;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pack.api.code.user.ApiUserErrorCode;
import pack.api.code.user.ApiUserSuccessCode;
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
                    .errorStatus(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorStatus())
                    .errorDivisionCode(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorDivisionCode())
                    .errorMsg(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorMsg())
                    .build();
            return ResponseEntity.status(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiUserSuccessCode.EMAIL_AVAILABLE.getUserApiStatus())
                    .resultMsg(ApiUserSuccessCode.EMAIL_AVAILABLE.getUserApiMessage())
                    .build();
            return ResponseEntity.status(ApiUserSuccessCode.EMAIL_AVAILABLE.getUserApiStatus()).body(emailCheckResponse);
        }
    }

    @GetMapping("/api/auth/user/userTelCheck")
    public ResponseEntity<Object> telPhoneCheck(@RequestParam String userTel) {
        log.info(" *****************************    telPhoneCheck START    *****************************");

        boolean userTelDuplicate = userService.isTelPhoneDuplicate(userTel);

        if (userTelDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiUserErrorCode.TELEPHONE_DUPLICATED.getUserErrorStatus())
                    .errorDivisionCode(ApiUserErrorCode.TELEPHONE_DUPLICATED.getUserErrorDivisionCode())
                    .errorMsg(ApiUserErrorCode.TELEPHONE_DUPLICATED.getUserErrorMsg())
                    .build();
            return ResponseEntity.status(ApiUserErrorCode.TELEPHONE_DUPLICATED.getUserErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> telPhoneCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiUserSuccessCode.TELEPHONE_AVAILABLE.getUserApiStatus())
                    .resultMsg(ApiUserSuccessCode.TELEPHONE_AVAILABLE.getUserApiMessage())
                    .build();
            return ResponseEntity.status(ApiUserSuccessCode.TELEPHONE_AVAILABLE.getUserApiStatus()).body(telPhoneCheckResponse);
        }
    }


}
