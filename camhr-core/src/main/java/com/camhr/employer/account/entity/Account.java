package com.camhr.employer.account.entity;

import java.math.BigDecimal;

/**
 * 企业资金信息，对应数据表E_ACCOUNT
 */
public class Account {

  private long employerId;
  private BigDecimal amount;//帐户余额

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
