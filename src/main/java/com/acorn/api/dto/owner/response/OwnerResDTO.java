package com.acorn.api.dto.owner.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OwnerResDTO {

    private Integer ownerId;

    private String ownerEmail;

    private String ownerBusinessNum;

    private String ownerNm;

    private String ownerTel;

    private String ownerCompanyName;

    private String ownerAddr;

    private LocalDateTime ownerCreated;

    private LocalDateTime ownerUpdated;
}