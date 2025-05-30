package com.acorn.api.repository.payment;

import com.acorn.api.entity.payment.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentRepository {

    Integer selectPaymentIdKey();

    void insertPayment(Payment payment);

    Payment selectPaymentByReservationId(@Param("paymentReservationId") Integer paymentReservationId);
}