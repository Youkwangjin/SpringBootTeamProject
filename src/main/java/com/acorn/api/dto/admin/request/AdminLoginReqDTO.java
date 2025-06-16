package com.acorn.api.dto.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminLoginReqDTO {

    private String adminEmail;

    private String adminPassword;
}