package com.acorn.api.dto.admin.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminResDTO {

    private Integer adminId;

    private String adminEmail;

    private String adminPassword;

    private String adminNm;
}