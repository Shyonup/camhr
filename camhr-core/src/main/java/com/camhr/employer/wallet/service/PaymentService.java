package com.camhr.employer.wallet.service;

import com.camhr.employer.wallet.entity.Payment;
import com.camhr.employer.wallet.entity.PaymentHistory;

public interface PaymentService {

  int addPayment(Payment payment);

  int addPaymentHistory(PaymentHistory history);

  Payment getPayment(Long paymentId);

  int updatePaymentStatus(Payment payment);

}
