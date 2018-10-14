package com.camhr.employer.product.entity;

import java.util.Date;

/**
 * <pre>
 * 公司 使用服务包 的记录
 * 对应数据库的 E_SERVICE_ITEM_TR 表
 *
 * Created by Allen on 2018/9/28.
 * </pre>
 */
public class EmployerProductItemTR {

  private long id;
  private long employerId;
  private long objectid;
  private long serviceItemId; // 外键，EmployerProductItem实体类 的 Id，E_SERVICE_ITEM 表的主键
  private int itemId;
  private int amount; // 使用数量
  private int balance; // 余额
  private Date cdate; // createAt
  private Date startDate;
  private Date endDate;

  // ==================================== get / set ====================================

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

  public long getObjectid() {
    return objectid;
  }

  public void setObjectid(long objectid) {
    this.objectid = objectid;
  }

  public long getServiceItemId() {
    return serviceItemId;
  }

  public void setServiceItemId(long serviceItemId) {
    this.serviceItemId = serviceItemId;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public Date getCdate() {
    return cdate;
  }

  public void setCdate(Date cdate) {
    this.cdate = cdate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
