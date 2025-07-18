package com.acorn.api.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginReqDTO {

    private String userEmail;

    private String userPassword;
}