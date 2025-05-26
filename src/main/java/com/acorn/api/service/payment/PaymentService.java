package com.acorn.api.service.payment;

import com.acorn.api.dto.payment.KakaoPayReadyResponseDTO;
import com.acorn.api.dto.payment.PaymentRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    KakaoPayReadyResponseDTO preparePayment(PaymentRequestDTO requestData);
}