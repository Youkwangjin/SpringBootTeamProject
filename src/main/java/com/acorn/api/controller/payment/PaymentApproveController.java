package com.acorn.api.controller.payment;

import com.acorn.api.dto.payment.kakaopay.response.KakaoPayApproveResDTO;
import com.acorn.api.dto.payment.request.PaymentApproveReqDTO;
import com.acorn.api.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PaymentApproveController {

    private final PaymentService paymentService;

    @GetMapping("/api/user/payments/approve")
    public String approvePayment(@Valid PaymentApproveReqDTO requestData) {
        KakaoPayApproveResDTO response = paymentService.approvePayment(requestData);

        return "redirect:/user/reservation/detail/" + Integer.parseInt(response.getPartner_order_id());
    }
}