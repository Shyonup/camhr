package com.camhr.employer.wallet.entity;


import com.camhr.constants.AccountType;
import com.camhr.employer.wallet.constants.PaymentMethod;
import com.camhr.employer.wallet.constants.PaymentStatus;
import com.camhr.employer.wallet.constants.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;
import we.json.AsMoney;

public class Payment {

  @JsonProperty(access = Access.READ_ONLY)
  @JsonSerialize(using = ToStringSerializer.class)
  private long paymentId; // 流水号

  @JsonProperty(access = Access.READ_ONLY)
  @JsonSerialize(using = ToStringSerializer.class)
  private long orderId; // 订单号

  @Length(max = 255)
  private String name;

  @AsConstant("PAYMENT_STATUS")
  private int status = PaymentStatus.OK.value();

  @Min(0)
  private int payerType = AccountType.EMPLOYER.value(); // 雇主或求职者

  @AsConstant("PAYMENT_TYPE")
  @Max(1)
  @Min(0)
  private int paymentType = PaymentType.COLLECT.value(); // 收款，退款，默认是收款

  @AsConstant("PAYMENT_METHOD")
  private int paymentMethod = PaymentMethod.BANK.value(); // 支付方式，默认是银行转账

  @Length(max = 10)
  private String currency = "USD"; // 货币符号

  @JsonProperty(access = Access.READ_ONLY)
  private int createAt;

  private int payAt; // 支付时间

  private int doneAt; // 入账时间

  private BigDecimal amount; // 发生额

  @Min(0)
  @JsonSerialize(using = ToStringSerializer.class)
  private long payerId; // 付款人Id

  @Length(max = 255)
  private String failureReason; // 交易失败原因

  @Length(max = 255)
  private String memo;

  private Map<String, Object> attributes = Maps.newHashMap();

  public long getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(long paymentId) {
    this.paymentId = paymentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public int getPayerType() {
    return payerType;
  }

  public void setPayerType(int payerType) {
    this.payerType = payerType;
  }

  public int getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(int paymentType) {
    this.paymentType = paymentType;
  }

  public int getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(int paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public int getCreateAt() {
    return createAt;
  }

  public void setCreateAt(int createAt) {
    this.createAt = createAt;
  }

  public int getPayAt() {
    return payAt;
  }

  public void setPayAt(int payAt) {
    this.payAt = payAt;
  }

  public int getDoneAt() {
    return doneAt;
  }

  public void setDoneAt(int doneAt) {
    this.doneAt = doneAt;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public long getPayerId() {
    return payerId;
  }

  public void setPayerId(long payerId) {
    this.payerId = payerId;
  }

  public String getFailureReason() {
    return failureReason;
  }

  public void setFailureReason(String failureReason) {
    this.failureReason = failureReason;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
}
