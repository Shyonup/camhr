package com.camhr.employer.product.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Min;
import we.json.AsConstant;

/**
 * <pre>
 * EmployerServiceItem就是 E_SERVICE_ITEM 表
 * Created by Allen on 2018/10/11.
 * </pre>
 */
public class EmployerServiceItem {

  /**
   * 当企业使用 免费发布职位 服务时，id是-2。（旧代码写成-2了）
   */
  public static final int FREE_PUBLISH_PRODUCT_ITEM_ID = -2;

  private long id;

  @Min(1)
  private long employerId;

  @Min(1)
  private int itemId;

  @Min(1)
  private int amount; // 购买的数量

  private Date expdate; // 有效期

  private int display; // 保质期，多少天

  private Date cdate;// creat_at

  private long pServiceId; // 产品ID

  private long pBillno; // 产品交易号

  @AsConstant("EMPLOYER_PRODUCT_ITEM_STATUS")
  private int status; // 0-正常，1-取消

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

  public Date getExpdate() {
    return expdate;
  }

  public void setExpdate(Date expdate) {
    this.expdate = expdate;
  }

  public int getDisplay() {
    return display;
  }

  public void setDisplay(int display) {
    this.display = display;
  }

  public Date getCdate() {
    return cdate;
  }

  public void setCdate(Date cdate) {
    this.cdate = cdate;
  }

  public long getpServiceId() {
    return pServiceId;
  }

  public void setpServiceId(long pServiceId) {
    this.pServiceId = pServiceId;
  }

  public long getpBillno() {
    return pBillno;
  }

  public void setpBillno(long pBillno) {
    this.pBillno = pBillno;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
