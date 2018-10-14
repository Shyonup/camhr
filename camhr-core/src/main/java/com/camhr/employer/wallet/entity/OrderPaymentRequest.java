package com.camhr.employer.wallet.entity;

public class OrderPaymentRequest {

  private long userId;
  private int accountType;
  private long orderId;
  private int paymentMethod;

  public long getUserId() {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public int getAccountType() {
    return accountType;
  }

  public void setAccountType(int accountType) {
    this.accountType = accountType;
  }

  public long getOrderId() {
    return this.orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public int getPaymentMethod() {
    return this.paymentMethod;
  }

  public void setPaymentMethod(int paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

}
