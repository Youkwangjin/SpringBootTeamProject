package pack.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.api.code.common.ApiHttpSuccessCode;
import pack.api.response.ApiSuccessResponse;
import pack.model.user.User;
import pack.service.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserUpdateController {

    private static final Logger log = LoggerFactory.getLogger(UserUpdateController.class);
    private final UserService userService;

    @PatchMapping("/api/user/update/{userUUId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userUpdate(@Valid @RequestBody User user) {

        log.info(" *****************************    User Update START    *****************************");

        userService.userDataUpdate(user);

        ApiSuccessResponse<Object> updateResponse = ApiSuccessResponse.builder()
                .resultCode(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getStatus())
                .resultMsg(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiHttpSuccessCode.INFO_UPDATE_SUCCESS.getStatus()).body(updateResponse);

    }

}
