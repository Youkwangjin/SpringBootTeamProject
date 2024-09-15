package com.acorn.api.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateDTO {

    private String userUUId;

    private String userPassword;

    private String userDisplayName;

    private String userTel;

    private String userAddr;

}
