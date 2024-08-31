package pack.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.api.code.common.ApiHttpSuccessCode;
import pack.api.response.ApiSuccessResponse;
import pack.model.user.User;
import pack.service.user.UserService;


@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterController.class);
    private final UserService userService;

    @PostMapping("/api/auth/user/register")
    public ResponseEntity<ApiSuccessResponse<Object>> userRegister(@Valid @RequestBody User user) {
        log.info(" *****************************    Register START    *****************************");

        userService.userRegister(user);

        ApiSuccessResponse<Object> registerResponse = ApiSuccessResponse.builder()
                .resultCode(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getStatus())
                .resultMsg(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiHttpSuccessCode.REGISTER_INSERT_SUCCESS.getStatus()).body(registerResponse);
    }
}
