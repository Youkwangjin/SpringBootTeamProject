package com.acorn.api.dto.reservation.request;

import com.acorn.api.dto.common.CommonListReqDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationContainerListReqDTO extends CommonListReqDTO {

    private Integer containerOwnerId;
}