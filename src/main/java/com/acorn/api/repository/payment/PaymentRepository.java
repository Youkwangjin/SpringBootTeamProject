package com.acorn.api.repository.payment;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.payment.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentRepository {

    Integer selectPaymentIdKey();

    Integer selectListCountByRequest(PaginationRequest pagination);

    List<Payment> selectPaymentListData(PaginationRequest pagination);

    Payment selectPaymentDetailData(@Param("paymentId") Integer paymentId);

    void insertPayment(Payment payment);

    Payment selectPaymentByReservationId(@Param("paymentReservationId") Integer paymentReservationId);
}