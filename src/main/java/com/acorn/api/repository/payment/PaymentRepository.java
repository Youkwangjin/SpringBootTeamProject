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

    Payment selectPaymentByReservationId(@Param("paymentReservationId") Integer paymentReservationId);

    List<Payment> selectPaymentByUserIdAndStatus(@Param("paymentUserId") Integer userId, @Param("paymentStatus") Integer paymentStatus);

    void insertPayment(Payment payment);

    void updatePayment(Payment payment);

    void updatePaymentStatus(Payment payment);

    void deletePayment(Payment payment);
}