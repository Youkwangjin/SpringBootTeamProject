package com.acorn.api.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {

    private int userId;

    private String userUUId;

    private String userEmail;

    private String userDisplayName;

    private String userTel;

    private String userAddr;
}
