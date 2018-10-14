package com.camhr.employer.wallet.service.impl;

import com.camhr.employer.account.constants.TradeType;
import com.camhr.employer.account.entity.AccountTrade;
import com.camhr.employer.account.service.AccountService;
import com.camhr.employer.wallet.builders.RechargeQueryBuider;
import com.camhr.employer.wallet.constants.PaymentStatus;
import com.camhr.employer.wallet.constants.PaymentStatusMapping;
import com.camhr.employer.wallet.constants.PaymentType;
import com.camhr.employer.wallet.constants.RechargeStatus;
import com.camhr.employer.wallet.constants.RechargeStatusMapping;
import com.camhr.employer.wallet.entity.OrderPaymentRequest;
import com.camhr.employer.wallet.entity.Payment;
import com.camhr.employer.wallet.entity.PaymentHistory;
import com.camhr.employer.wallet.entity.Recharge;
import com.camhr.employer.wallet.mapper.RechargeMapper;
import com.camhr.employer.wallet.service.PaymentService;
import com.camhr.employer.wallet.service.RechargeService;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import we.lang.Exceptions;
import we.lang.IdWorker;
import we.lang.Numbers;
import we.lang.Q;
import we.payment.PaymentResponse;
import we.payment.events.NotifyEvent;
import we.payment.mpay.MPayTemplate;
import we.payment.mpay.MPaymentRequest;
import we.payment.mpay.PaymentTypeMapping;
import we.util.Page;


@Service
public class RechargeServiceImpl implements RechargeService {

  private static IdWorker idWorker = new IdWorker(1, 1, 1);

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private RechargeMapper rechargeMapper;

  @Autowired
  private MPayTemplate mPayTemplate;

  @Autowired
  private AccountService accountService;

  @Override
  public int addRecharge(Recharge recharge) {
    recharge.setStatus(RechargeStatus.NOPAY.value());
    recharge.setCdate(new Date());
    recharge.setPayTime(recharge.getCdate());
    recharge.setDescription("");
    return rechargeMapper.addRecharge(recharge);
  }

  @Override
  public Recharge getRecharge(long orderId) {
    return rechargeMapper.getRecharge(orderId);
  }

  @Override
  public PaymentResponse payRecharge(OrderPaymentRequest orderPaymentRequest) {
    Recharge recharge = rechargeMapper.getRecharge(orderPaymentRequest.getOrderId());
    if (recharge == null) {
      Exceptions.of(Recharge.class).notFound("${recharge.order.notfound:未知充值订单信息}");
    }
    /*生成流水信息*/
    Payment payment = new Payment();
    payment.setPaymentId(idWorker.nextId());
    payment.setOrderId(recharge.getId());
    payment.setPaymentType(PaymentType.COLLECT.value());
    payment.setAmount(recharge.getCash());
    payment.setPayerId(orderPaymentRequest.getUserId());
    payment.setStatus(PaymentStatus.PRE.value());
    payment.setPaymentMethod(orderPaymentRequest.getPaymentMethod());
    payment.setCreateAt(Q.now());
    paymentService.addPayment(payment);

    MPaymentRequest request = new MPaymentRequest();
    request
        .setPayType(PaymentTypeMapping.of(orderPaymentRequest.getPaymentMethod()).getPaymentName());
    request.setPaymentId(String.valueOf(payment.getPaymentId()));
    request.setOrderId(String.valueOf(payment.getOrderId()));
    request.setSubject(payment.getName());
    request.setTotalFee(payment.getAmount().toString());
    PaymentResponse response = mPayTemplate.doPayment(request);
    /*save payment History*/
    PaymentHistory history = new PaymentHistory();
    history.setCreateAt(Q.now());
    history.setPaymentId(Numbers.toLong(response.getPaymentId()));
    history.setResult(response.getResult());
    history.setStatus(response.getStatus().ordinal());
    paymentService.addPaymentHistory(history);
    return response;
  }

  @Override
  public Page<Recharge> queryRecharges(RechargeQueryBuider rechargeQueryBuider) {
    Page<Recharge> page = Page.of(rechargeQueryBuider);
    page.setTotalCount(rechargeMapper.countRecharge(rechargeQueryBuider));
    page.setResult(rechargeMapper.queryRecharge(rechargeQueryBuider));
    return page;
  }

  @EventListener
  public void onPaymentResponseNotify(NotifyEvent notifyEvent) {
    PaymentResponse response = notifyEvent.getPaymentResponse();
    Payment payment = paymentService.getPayment(Long.valueOf(response.getPaymentId()));
    if (payment != null) {
      payment.setStatus(PaymentStatusMapping.of(response.getStatus()).getPaymentStatus().value());
      paymentService.updatePaymentStatus(payment);
      /*save payment History*/
      PaymentHistory history = new PaymentHistory();
      history.setCreateAt(Q.now());
      history.setPaymentId(Numbers.toLong(response.getPaymentId()));
      history.setResult(response.getResult());
      history.setStatus(response.getStatus().ordinal());
      paymentService.addPaymentHistory(history);
      //更新order
      if (payment.getStatus() == PaymentStatus.OK.value()) {
        Recharge recharge = getRecharge(payment.getOrderId());
        recharge
            .setStatus(RechargeStatusMapping.of(response.getStatus()).getRechargeStatus().value());
        recharge.setRealPayTime(new Date());
        rechargeMapper.updateRechargeStatus(recharge);
        //添加积分
        AccountTrade accountTrade = new AccountTrade();
        accountTrade.setAmount(payment.getAmount());
        accountTrade.setType(TradeType.ONLINE_PAY.value());
        accountTrade.setEmployerId(recharge.getEmployerId());
        accountTrade.setBillno(String.valueOf(payment.getOrderId()));
        //设置非null值，不然数据库报错
        accountTrade.setDescription("");
        accountTrade.setCardno("");
        accountTrade.setDiscount(new BigDecimal(0));
        accountService.addAccountTrade(accountTrade);
      }
    }
  }
}
