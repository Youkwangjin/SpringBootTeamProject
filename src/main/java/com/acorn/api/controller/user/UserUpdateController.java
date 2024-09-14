package com.acorn.api.controller.user;


import com.acorn.api.com.acorn.api.code.code.common.ApiHttpSuccessCode;
import com.acorn.api.com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.model.user.User;
import com.acorn.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
