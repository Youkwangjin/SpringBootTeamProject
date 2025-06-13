package com.acorn.api.dto.payment.kakaopay.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayReadyResDTO {

    private String tid;

    private String next_redirect_pc_url;
}