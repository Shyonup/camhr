package com.camhr.employer.product.entity;

import io.swagger.annotations.ApiModelProperty;
import we.json.AsConstant;

/**
 * <pre>
 * 对应 P_SERVICE_ITEM 表
 *
 * Created by Allen on 2018/9/29.
 * </pre>
 */
public class ProductItem {

  private int id;

  private int serviceId;

  @AsConstant("ITEM")
  private int itemId;

  @ApiModelProperty("数量")
  private int amount;

  @ApiModelProperty("有效期：天，购买算起")
  private int expdate;

  @ApiModelProperty("显示天数")
  private int display;

  // ==================================== get / set ====================================

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
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

  public int getExpdate() {
    return expdate;
  }

  public void setExpdate(int expdate) {
    this.expdate = expdate;
  }

  public int getDisplay() {
    return display;
  }

  public void setDisplay(int display) {
    this.display = display;
  }
}
