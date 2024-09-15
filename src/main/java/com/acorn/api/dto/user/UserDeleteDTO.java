package com.acorn.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeleteDTO {

    private String userUUId;

    private String userPassword;

}
