package pack.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.api.common.ApiSuccessCode;
import pack.api.response.ApiSuccessResponse;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    @PostMapping("/auth/user/register")
    public ResponseEntity<ApiSuccessResponse<Object>> userRegister(@Valid @RequestBody UserDTO userDTO) {
        log.info(" *****************************    Register START    *****************************");

        userService.userRegister(userDTO);

        ApiSuccessResponse<Object> registerResponse = ApiSuccessResponse.builder()
                        .resultCode(ApiSuccessCode.REGISTER_INSERT_SUCCESS.getStatus())
                        .resultMsg(ApiSuccessCode.REGISTER_INSERT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
}
