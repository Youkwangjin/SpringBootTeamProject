package com.acorn.api.model.owner;

import com.acorn.api.role.OwnerRole;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class Owner {
    /*
        애플리케이션의 비즈니스 로직과 데이터 관리를 처리
     */
    private int ownerId;

    private String ownerUUId;

    private String ownerEmail;

    private String ownerBusinessNum;

    private String ownerPassword;

    private String ownerName;

    private String ownerTel;

    private String ownerCompanyName;

    private String ownerAddr;

    private OwnerRole ownerRole;

}
