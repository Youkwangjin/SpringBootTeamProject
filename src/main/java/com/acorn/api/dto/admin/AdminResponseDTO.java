package com.acorn.api.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminResponseDTO {

    private Integer adminId;

    private String adminEmail;

    private String adminPassword;

    private String adminNm;
}