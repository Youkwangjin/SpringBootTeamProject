package com.acorn.api.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerLoginDTO {

    private String ownerBusinessNum;

    private String ownerPassword;
}