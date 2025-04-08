package com.acorn.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO {

    private String adminEmail;

    private String adminPassword;
}