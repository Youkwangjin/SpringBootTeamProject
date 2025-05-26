package com.acorn.api.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayReadyResponseDTO {

    private String tid;

    private String next_redirect_pc_url;
}