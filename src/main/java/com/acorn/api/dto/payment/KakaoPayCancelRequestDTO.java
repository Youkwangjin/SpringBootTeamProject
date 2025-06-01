package com.acorn.api.dto.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPayCancelRequestDTO {

    private String cid;

    private String tid;

    private Integer cancel_amount;

    private Integer cancel_tax_free_amount;

    private Integer cancel_vat_amount;

    private Integer cancel_available_amount;

    private String payload;
}