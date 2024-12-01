package com.acorn.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDTO {

    private String userEmail;

    private String userPassword;
}