package com.acorn.api.dto.container.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ContainerDeleteReqDTO {

    @NotNull
    @Positive
    private Integer containerId;

    @NotNull
    @Positive
    private Integer ownerId;
}