package com.acorn.api.dto.admin.response;

import com.acorn.api.dto.container.response.ContainerDetailResDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminContainerDetailResDTO {

    private ContainerDetailResDTO container;

    private OwnerResDTO owner;
}