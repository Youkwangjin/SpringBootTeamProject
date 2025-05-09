package com.acorn.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContainerManagementRequestDTO {

    private Integer containerId;

    private Integer ownerId;
}