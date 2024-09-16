package com.acorn.api.dto.owner;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerLoginDTO {

    private String ownerBusinessNum;

    private String ownerPassword;
}
