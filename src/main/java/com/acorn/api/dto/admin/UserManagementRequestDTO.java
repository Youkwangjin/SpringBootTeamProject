package com.acorn.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementRequestDTO {

    private Integer userId;

    private String userEmail;

    private String userNm;

    private String userTel;

    private String userAddr;
}