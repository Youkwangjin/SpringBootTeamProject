package com.acorn.api.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {

    private Integer userId;

    private String userEmail;

    private String userNm;

    private String userTel;

    private String userAddr;
}