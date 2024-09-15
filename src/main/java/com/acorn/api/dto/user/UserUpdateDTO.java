package com.acorn.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateDTO {

    private String userUUId;

    private String userPassword;

    private String userDisplayName;

    private String userTel;

    private String userAddr;

}
