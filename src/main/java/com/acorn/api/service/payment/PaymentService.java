package com.acorn.api.service.payment;

import com.acorn.api.dto.payment.KakaoPayApproveResponseDTO;
import com.acorn.api.dto.payment.KakaoPayReadyResponseDTO;
import com.acorn.api.dto.payment.PaymentApproveRequestDTO;
import com.acorn.api.dto.payment.PaymentReadyRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    KakaoPayReadyResponseDTO preparePayment(PaymentReadyRequestDTO requestData);

    KakaoPayApproveResponseDTO approvePayment(PaymentApproveRequestDTO requestData);
}