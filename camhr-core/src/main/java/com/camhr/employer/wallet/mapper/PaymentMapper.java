package com.camhr.employer.wallet.mapper;

import com.camhr.employer.wallet.entity.Payment;
import com.camhr.employer.wallet.entity.PaymentHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

  int addPayment(Payment payment);

  int addPaymentHistory(PaymentHistory history);

  int updatePaymentStatus(Payment payment);
}
