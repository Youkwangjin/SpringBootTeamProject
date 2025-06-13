package com.acorn.api.dto.payment.kakaopay.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayAmountDTO {

    private Integer total;

    private Integer tax_free;

    private Integer vat;

    private Integer point;

    private Integer discount;

    private Integer green_deposit;
}