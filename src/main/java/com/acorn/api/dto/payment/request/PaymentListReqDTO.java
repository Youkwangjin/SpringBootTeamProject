package com.acorn.api.dto.payment.request;

import com.acorn.api.dto.common.CommonListReqDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentListReqDTO extends CommonListReqDTO {

    private Integer paymentUserId;
}