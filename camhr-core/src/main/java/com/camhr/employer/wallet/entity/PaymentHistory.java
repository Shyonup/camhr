package com.camhr.employer.wallet.entity;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * 流水历史表
 */
public class PaymentHistory {

  private long paymentId;

  private int paymentMethod;

  private int status;

  private int createAt;

  private Map<String, Object> result = Maps.newHashMap();

  private String failureReason; // 失败原因

  public long getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(long paymentId) {
    this.paymentId = paymentId;
  }

  public int getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(int paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getCreateAt() {
    return createAt;
  }

  public void setCreateAt(int createAt) {
    this.createAt = createAt;
  }

  public Map<String, Object> getResult() {
    return result;
  }

  public void setResult(Map<String, Object> result) {
    this.result = result;
  }

  public String getFailureReason() {
    return failureReason;
  }

  public void setFailureReason(String failureReason) {
    this.failureReason = failureReason;
  }
}
