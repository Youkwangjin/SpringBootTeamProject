package com.acorn.api.entity.owner;

import com.acorn.api.role.OwnerRole;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Owner {

    private Integer ownerId;

    private String ownerEmail;

    private String ownerBusinessNum;

    private String ownerPassword;

    private String ownerNm;

    private String ownerTel;

    private String ownerCompanyName;

    private String ownerAddr;

    private OwnerRole ownerRole;
}