package com.acorn.api.entity.user;

import com.acorn.api.role.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer rowNum;

    private Integer userId;

    private String userEmail;

    private String userPassword;

    private String userNm;

    private String userTel;

    private String userAddr;

    private UserRole userRole;

    private LocalDateTime userCreated;

    private LocalDateTime userUpdated;
}