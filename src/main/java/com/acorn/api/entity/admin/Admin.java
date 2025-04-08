package com.acorn.api.entity.admin;

import com.acorn.api.role.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    private Integer adminId;

    private String adminEmail;

    private String adminPassword;

    private String adminNm;

    private AdminRole adminRole;
}