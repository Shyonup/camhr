package com.camhr.employer.wallet.service;

import com.camhr.employer.wallet.builders.RechargeQueryBuider;
import com.camhr.employer.wallet.entity.OrderPaymentRequest;
import com.camhr.employer.wallet.entity.Recharge;
import we.payment.PaymentResponse;
import we.util.Page;

public interface RechargeService {

  int addRecharge(Recharge recharge);

  Recharge getRecharge(long orderId);

  PaymentResponse payRecharge(OrderPaymentRequest orderPaymentRequest);

  Page<Recharge> queryRecharges(RechargeQueryBuider rechargeQueryBuider);
}
