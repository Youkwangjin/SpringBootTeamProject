package com.acorn.api.dto.user.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResDTO {

    private Integer userId;

    private String userEmail;

    private String userNm;

    private String userTel;

    private String userAddr;

    private LocalDateTime userCreated;

    private LocalDateTime userUpdated;
}