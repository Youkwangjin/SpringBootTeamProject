package com.acorn.api.dto.owner;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OwnerResponseDTO {

    private int ownerId;

    private String ownerUUId;

    private String ownerEmail;

    private String ownerBusinessNum;

    private String ownerName;

    private String ownerTel;

    private String ownerCompanyName;

    private String ownerAddr;
}