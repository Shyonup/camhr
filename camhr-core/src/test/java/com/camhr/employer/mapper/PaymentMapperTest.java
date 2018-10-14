package com.camhr.employer.mapper;

import com.camhr.employer.wallet.constants.PaymentMethod;
import com.camhr.employer.wallet.constants.PaymentStatus;
import com.camhr.employer.wallet.entity.Payment;
import com.camhr.employer.wallet.entity.PaymentHistory;
import com.camhr.employer.wallet.mapper.PaymentMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Q;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PaymentMapperTest {

  @Autowired
  private PaymentMapper paymentMapper;

  private Payment payment = new Payment();
  private PaymentHistory paymentHistory = new PaymentHistory();

  @Test
  public void addPayment() {
    payment.setPaymentId(System.currentTimeMillis());
    payment.setOrderId(payment.getPaymentId());
    payment.setPayerId(13383);
    payment.setAmount(new BigDecimal(33));
    payment.setCreateAt(Q.now());
    payment.setCurrency("USD");
    payment.setDoneAt(Q.now());
    payment.setFailureReason("网关超时");
    payment.setMemo("时限");
    payment.setStatus(PaymentStatus.OK.value());
    payment.setPaymentType(1);
    payment.setPayerType(1);
    payment.setPaymentMethod(PaymentMethod.ALIPAY.value());
    payment.setName("账单流水");
    payment.setPayAt(Q.now());
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("a", "b");
    payment.setAttributes(attributes);
    int rows = paymentMapper.addPayment(payment);
    Assert.assertEquals(1, rows);
  }

  @Test
  public void addPaymentHistory() {
    paymentHistory.setPaymentId(System.currentTimeMillis());
    paymentHistory.setCreateAt(Q.now());
    paymentHistory.setStatus(2);
    paymentHistory.setFailureReason("网关超时");
    paymentHistory.setPaymentMethod(1);
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("a", "b");
    paymentHistory.setResult(attributes);
    int rows = paymentMapper.addPaymentHistory(paymentHistory);
    Assert.assertEquals(1, rows);
  }
}
