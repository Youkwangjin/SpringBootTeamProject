package com.acorn.api.dto.payment.kakaopay.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPayApproveReqDTO {

    private String cid;

    private String tid;

    private String partner_order_id;

    private String partner_user_id;

    private String pg_token;
}