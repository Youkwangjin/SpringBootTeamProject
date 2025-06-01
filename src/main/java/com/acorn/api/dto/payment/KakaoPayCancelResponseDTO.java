package com.acorn.api.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoPayCancelResponseDTO {

    private String aid;

    private String tid;

    private String cid;

    private String status;

    private String partner_order_id;

    private String partner_user_id;

    private String payment_method_type;

    private String item_name;

    private String item_code;

    private Integer quantity;

    private LocalDateTime created_at;

    private LocalDateTime approved_at;

    private LocalDateTime canceled_at;

    private String payload;

    private Amount amount;

    private ApprovedCancelAmount approved_cancel_amount;

    private CanceledAmount canceled_amount;

    private CancelAvailableAmount cancel_available_amount;

    @Getter
    @Setter
    public static class Amount {
        private Integer total;

        private Integer tax_free;

        private Integer vat;

        private Integer point;

        private Integer discount;

        private Integer green_deposit;
    }

    @Getter
    @Setter
    public static class ApprovedCancelAmount {
        private Integer total;

        private Integer tax_free;

        private Integer vat;

        private Integer point;

        private Integer discount;

        private Integer green_deposit;
    }

    @Getter
    @Setter
    public static class CanceledAmount {
        private Integer total;

        private Integer tax_free;

        private Integer vat;

        private Integer point;

        private Integer discount;

        private Integer green_deposit;
    }

    @Getter
    @Setter
    public static class CancelAvailableAmount {

        private Integer total;

        private Integer tax_free;

        private Integer vat;

        private Integer point;

        private Integer discount;

        private Integer green_deposit;
    }
}