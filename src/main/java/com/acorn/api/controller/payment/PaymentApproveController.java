package com.acorn.api.controller.payment;

import com.acorn.api.dto.payment.KakaoPayApproveResponseDTO;
import com.acorn.api.dto.payment.PaymentApproveRequestDTO;
import com.acorn.api.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentApproveController {

    private final PaymentService paymentService;

    @GetMapping("/api/user/payments/approve")
    public String approvePayment(@Valid PaymentApproveRequestDTO requestData) {
        log.info(" *****************************    User Payment Approve START    *****************************");

        KakaoPayApproveResponseDTO response = paymentService.approvePayment(requestData);

        return "redirect:/user/reservation/detail/" + Integer.parseInt(response.getPartner_order_id());
    }
}