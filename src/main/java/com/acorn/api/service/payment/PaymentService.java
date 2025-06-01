package com.acorn.api.service.payment;

import com.acorn.api.dto.payment.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    List<PaymentListDTO> getPaymentListData(PaymentListDTO listData);

    PaymentDetailDTO getPaymentData(Integer paymentId);

    KakaoPayReadyResponseDTO preparePayment(PaymentReadyRequestDTO requestData);

    KakaoPayApproveResponseDTO approvePayment(PaymentApproveRequestDTO requestData);

    void cancelPayment(PaymentCancelRequestDTO requestData);
}