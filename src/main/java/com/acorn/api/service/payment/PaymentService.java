package com.acorn.api.service.payment;

import com.acorn.api.dto.payment.kakaopay.response.KakaoPayApproveResDTO;
import com.acorn.api.dto.payment.kakaopay.response.KakaoPayReadyResDTO;
import com.acorn.api.dto.payment.request.PaymentApproveReqDTO;
import com.acorn.api.dto.payment.request.PaymentCancelReqDTO;
import com.acorn.api.dto.payment.request.PaymentListReqDTO;
import com.acorn.api.dto.payment.request.PaymentReadyReqDTO;
import com.acorn.api.dto.payment.response.PaymentDetailResDTO;
import com.acorn.api.dto.payment.response.PaymentListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    List<PaymentListResDTO> getPaymentListData(PaymentListReqDTO listData);

    PaymentDetailResDTO getPaymentData(Integer paymentId);

    KakaoPayReadyResDTO preparePayment(PaymentReadyReqDTO requestData);

    KakaoPayApproveResDTO approvePayment(PaymentApproveReqDTO requestData);

    void cancelPayment(PaymentCancelReqDTO requestData);
}