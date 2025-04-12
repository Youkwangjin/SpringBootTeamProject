package com.acorn.api.dto.admin;

import com.acorn.api.dto.container.ContainerDetailDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminContainerDetailResponseDTO {

    private ContainerDetailDTO container;

    private OwnerResponseDTO owner;
}