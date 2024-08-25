package pack.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.api.code.common.ApiSuccessCode;
import pack.api.response.ApiSuccessResponse;
import pack.model.user.User;
import pack.service.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserDeleteController {

    private static final Logger log = LoggerFactory.getLogger(UserDeleteController.class);
    private final UserService userService;

    @DeleteMapping("/api/user/delete/{userUUId}")
    public ResponseEntity<ApiSuccessResponse<Object>> userDelete(@Valid @RequestBody User user) {

        log.info(" *****************************    User Delete START    *****************************");

        userService.userDataDelete(user);

        ApiSuccessResponse<Object> deleteResponse = ApiSuccessResponse.builder()
                .resultCode(ApiSuccessCode.INFO_DELETE_SUCCESS.getStatus())
                .resultMsg(ApiSuccessCode.INFO_DELETE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(ApiSuccessCode.INFO_DELETE_SUCCESS.getStatus()).body(deleteResponse);

    }


}
