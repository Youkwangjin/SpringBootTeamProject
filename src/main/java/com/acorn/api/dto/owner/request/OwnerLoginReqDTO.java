package com.acorn.api.dto.owner.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerLoginReqDTO {

    private String ownerBusinessNum;

    private String ownerPassword;
}