package com.acorn.api.controller.payment;

import com.acorn.api.dto.payment.PaymentListDTO;
import com.acorn.api.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentPageController {

    private final PaymentService paymentService;

    @GetMapping("/user/payment/list")
    public String paymentList(PaymentListDTO listData, Model model) {
        List<PaymentListDTO> paymentListData = paymentService.getPaymentListData(listData);
        model.addAttribute("paymentListData", paymentListData);
        model.addAttribute("request", listData);
        return "payment/payment-list";
    }
}