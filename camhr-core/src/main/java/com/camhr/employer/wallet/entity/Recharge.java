package com.camhr.employer.wallet.entity;

import com.camhr.employer.wallet.constants.RechargeStatus;
import com.camhr.employer.wallet.constants.PaymentMethod;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值订单
 */
public class Recharge {
  private long id;
  private int employerId;
  private int type = PaymentMethod.BANK.value();//充值类型：1.银行转账，2、现金，3.支票， 8.在线支付
  private int status = RechargeStatus.NOPAY.value();//0.未付，1-支付，2-已开通，未付,9-删除
  private BigDecimal cash;//充值金额
  private BigDecimal cashrep;//实际充值金额
  private Date payTime;//支付时间
  private String description;//订单描述
  private String fromaccount;//充值来源，卡号/银行帐号
  private Date cdate;//
  private int bgUserId;//后台开通操作员
  private String toaccountid;//转入账户，camHR银行账号编号
  private int remitter;//汇款人
  private String frombank;//转账银行
  private String remark;//备注
  private String checkno;//支票号
  private String draweraccount;//出票人账号
  private String paybank;//付款银行
  private String payee;//付款人
  private String contactname;//联系人
  private String company;//公司名称
  private String phone;//联系人电话
  private String address;//联系人电话
  private int pServiceId;//产品ID
  private int pNumber;//产品数量
  private int salesId;//业务员
  private Date realPayTime;//实际充值时间
  private int realBgUserId;//实际充值后台开通操作员
  private String receiptNo;//收据号
  private Date paymentDeadline;//支付期限
  private int vat;//税
  private String cardno;//

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getEmployerId() {
    return employerId;
  }

  public void setEmployerId(int employerId) {
    this.employerId = employerId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public BigDecimal getCash() {
    return cash;
  }

  public void setCash(BigDecimal cash) {
    this.cash = cash;
  }

  public BigDecimal getCashrep() {
    return cashrep;
  }

  public void setCashrep(BigDecimal cashrep) {
    this.cashrep = cashrep;
  }

  public Date getPayTime() {
    return payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFromaccount() {
    return fromaccount;
  }

  public void setFromaccount(String fromaccount) {
    this.fromaccount = fromaccount;
  }

  public Date getCdate() {
    return cdate;
  }

  public void setCdate(Date cdate) {
    this.cdate = cdate;
  }

  public int getBgUserId() {
    return bgUserId;
  }

  public void setBgUserId(int bgUserId) {
    this.bgUserId = bgUserId;
  }

  public String getToaccountid() {
    return toaccountid;
  }

  public void setToaccountid(String toaccountid) {
    this.toaccountid = toaccountid;
  }

  public int getRemitter() {
    return remitter;
  }

  public void setRemitter(int remitter) {
    this.remitter = remitter;
  }

  public String getFrombank() {
    return frombank;
  }

  public void setFrombank(String frombank) {
    this.frombank = frombank;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getCheckno() {
    return checkno;
  }

  public void setCheckno(String checkno) {
    this.checkno = checkno;
  }

  public String getDraweraccount() {
    return draweraccount;
  }

  public void setDraweraccount(String draweraccount) {
    this.draweraccount = draweraccount;
  }

  public String getPaybank() {
    return paybank;
  }

  public void setPaybank(String paybank) {
    this.paybank = paybank;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }

  public String getContactname() {
    return contactname;
  }

  public void setContactname(String contactname) {
    this.contactname = contactname;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getpServiceId() {
    return pServiceId;
  }

  public void setpServiceId(int pServiceId) {
    this.pServiceId = pServiceId;
  }

  public int getpNumber() {
    return pNumber;
  }

  public void setpNumber(int pNumber) {
    this.pNumber = pNumber;
  }

  public int getSalesId() {
    return salesId;
  }

  public void setSalesId(int salesId) {
    this.salesId = salesId;
  }

  public Date getRealPayTime() {
    return realPayTime;
  }

  public void setRealPayTime(Date realPayTime) {
    this.realPayTime = realPayTime;
  }

  public int getRealBgUserId() {
    return realBgUserId;
  }

  public void setRealBgUserId(int realBgUserId) {
    this.realBgUserId = realBgUserId;
  }

  public String getReceiptNo() {
    return receiptNo;
  }

  public void setReceiptNo(String receiptNo) {
    this.receiptNo = receiptNo;
  }

  public Date getPaymentDeadline() {
    return paymentDeadline;
  }

  public void setPaymentDeadline(Date paymentDeadline) {
    this.paymentDeadline = paymentDeadline;
  }

  public int getVat() {
    return vat;
  }

  public void setVat(int vat) {
    this.vat = vat;
  }

  public String getCardno() {
    return cardno;
  }

  public void setCardno(String cardno) {
    this.cardno = cardno;
  }
}
