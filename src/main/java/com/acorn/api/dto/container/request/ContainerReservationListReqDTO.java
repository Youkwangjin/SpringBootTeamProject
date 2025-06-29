package com.acorn.api.dto.container.request;

import com.acorn.api.dto.common.CommonListReqDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContainerReservationListReqDTO extends CommonListReqDTO {

    private Integer containerOwnerId;
}