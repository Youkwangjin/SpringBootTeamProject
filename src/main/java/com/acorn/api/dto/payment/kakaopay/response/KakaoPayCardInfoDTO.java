package com.acorn.api.dto.payment.kakaopay.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayCardInfoDTO {

    private String kakaopay_purchase_corp;

    private String kakaopay_issuer_corp;

    private String card_type;

    private String approved_id;
}