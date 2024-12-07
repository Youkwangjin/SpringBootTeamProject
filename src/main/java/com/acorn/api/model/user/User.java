package com.acorn.api.model.user;

import com.acorn.api.role.UserRole;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class User {
    /*
        애플리케이션의 비즈니스 로직과 데이터 관리를 처리
     */
    private Integer userId;

    private String userUUId;

    private String userEmail;

    private String userPassword;

    private String userNm;

    private String userTel;

    private String userAddr;

    private UserRole userRole;
}
