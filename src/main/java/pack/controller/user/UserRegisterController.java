package pack.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.common.enums.SuccessCode;
import pack.common.response.ApiResponse;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody UserDTO userDTO) {
        log.info(" *****************************    Register START    *****************************");

        userService.userRegister(userDTO);

        ApiResponse<Object> registerResponse = ApiResponse.builder()
                        .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                        .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
}
