package com.camhr.employer.account.entity;

import com.camhr.employer.account.constants.TradeType;
import java.math.BigDecimal;
import java.util.Date;
import we.json.AsConstant;

/**
 * 企业资金消费记录，对应数据表E_ACCOUNT_TR
 */
public class AccountTrade {

  private long id;
  private long employerId;
  @AsConstant("TRADE_TYPE")
  private int type = TradeType.BANK.value();//交易类型
  private BigDecimal amount;//交易数据，分：正，负，充值+，消费-
  private BigDecimal balance;//交易后账户余额
  private String billno;//交易订单号
  private String description;//备注
  private Date cdate;//交易时间
  private boolean iscancel;//0-正常，1-取消
  private BigDecimal discount = new BigDecimal("0");//优惠
  private String cardno;//优惠卡号

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getBillno() {
    return billno;
  }

  public void setBillno(String billno) {
    this.billno = billno;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCdate() {
    return cdate;
  }

  public void setCdate(Date cdate) {
    this.cdate = cdate;
  }

  public boolean isIscancel() {
    return iscancel;
  }

  public void setIscancel(boolean iscancel) {
    this.iscancel = iscancel;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public String getCardno() {
    return cardno;
  }

  public void setCardno(String cardno) {
    this.cardno = cardno;
  }
}
