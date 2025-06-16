package com.acorn.api.dto.admin.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminOwnerListResDTO {

    private Integer rowNum;

    private Integer ownerId;

    private String ownerBusinessNum;

    private String ownerNm;

    private String ownerTel;

    private String ownerCompanyName;

    private LocalDateTime ownerCreated;

    private LocalDateTime ownerUpdated;
}