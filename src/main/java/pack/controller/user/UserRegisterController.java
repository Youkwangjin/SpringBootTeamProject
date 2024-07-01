package pack.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.api.common.ApiSuccessCode;
import pack.api.response.ApiErrorResponse;
import pack.api.response.ApiSuccessResponse;
import pack.api.user.ApiUserErrorCode;
import pack.api.user.ApiUserSuccessCode;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;


    @GetMapping("/emailCheck")
    public ResponseEntity<Object> emailCheck(@RequestParam String userEmail) {
        log.info(" *****************************    emailCheck START    *****************************");

        boolean emailDuplicate = userService.isEmailDuplicate(userEmail);

        if (emailDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorStatus())
                    .errorDivisionCode(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorDivisionCode())
                    .errorMsg(ApiUserErrorCode.EMAIL_DUPLICATED.getUserErrorMsg())
                    .build();
            return ResponseEntity.status(ApiUserErrorCode.EMAIL_FORMAT_ERROR.getUserErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiUserSuccessCode.EMAIL_AVAILABLE.getUserApiStatus())
                    .resultMsg(ApiUserSuccessCode.EMAIL_AVAILABLE.getUserApiMessage())
                    .build();
            return ResponseEntity.status(ApiUserSuccessCode.EMAIL_AVAILABLE.getUserApiStatus()).body(emailCheckResponse);
        }
    }

    @PostMapping("/auth/user/register")
    public ResponseEntity<ApiSuccessResponse<Object>> userRegister(@Valid @RequestBody UserDTO userDTO) {
        log.info(" *****************************    Register START    *****************************");

        userService.userRegister(userDTO);

        ApiSuccessResponse<Object> registerResponse = ApiSuccessResponse.builder()
                .resultCode(ApiSuccessCode.REGISTER_INSERT_SUCCESS.getStatus())
                .resultMsg(ApiSuccessCode.REGISTER_INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiSuccessCode.REGISTER_INSERT_SUCCESS.getStatus()).body(registerResponse);
    }
}
