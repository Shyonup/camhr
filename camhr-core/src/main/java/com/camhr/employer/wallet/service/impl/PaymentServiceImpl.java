package com.camhr.employer.wallet.service.impl;

import com.camhr.employer.wallet.entity.Payment;
import com.camhr.employer.wallet.entity.PaymentHistory;
import com.camhr.employer.wallet.mapper.PaymentMapper;
import com.camhr.employer.wallet.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentMapper paymentMapper;

  @Override
  public int addPayment(Payment payment) {
    return paymentMapper.addPayment(payment);
  }

  @Override
  public int addPaymentHistory(PaymentHistory history) {
    return paymentMapper.addPaymentHistory(history);
  }

  @Override
  public Payment getPayment(Long paymentId) {
    return null;
  }

  @Override
  public int updatePaymentStatus(Payment payment) {
    return paymentMapper.updatePaymentStatus(payment);
  }
}
