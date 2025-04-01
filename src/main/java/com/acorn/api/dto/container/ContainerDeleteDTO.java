package com.acorn.api.dto.container;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerDeleteDTO {

    @NotNull
    private Integer containerId;

    @NotNull
    private Integer ownerId;
}