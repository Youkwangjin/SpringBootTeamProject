package com.acorn.api.model.user;

import com.acorn.api.role.UserRole;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class User {

    private Integer userId;

    private String userEmail;

    private String userPassword;

    private String userNm;

    private String userTel;

    private String userAddr;

    private UserRole userRole;
}