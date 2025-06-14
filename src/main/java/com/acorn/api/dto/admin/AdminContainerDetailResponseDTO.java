package com.acorn.api.dto.admin;

import com.acorn.api.dto.container.ContainerDetailDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminContainerDetailResponseDTO {

    private ContainerDetailDTO container;

    private OwnerResDTO owner;
}