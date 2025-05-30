package com.acorn.api.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoPayApproveResponseDTO {

    private String aid;

    private String tid;

    private String cid;

    private String partner_order_id;

    private String partner_user_id;

    private String payment_method_type;

    private String item_name;

    private Integer quantity;

    private KakaoPayAmountDTO amount;

    private KakaoPayCardInfoDTO card_info;

    private LocalDateTime approved_at;
}