package pack.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.dto.user.UserDTO;
import pack.service.user.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        log.info(" *****************************    START    *****************************");
        userService.userRegister(userDTO);
        return ResponseEntity.ok().body("Register Successful");
    }
}
