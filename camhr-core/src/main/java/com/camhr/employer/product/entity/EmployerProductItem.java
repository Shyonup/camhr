package com.camhr.employer.product.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * 公司 购买的服务项
 * 对应数据库的 E_SERVICE_ITEM 表
 *
 * Created by Allen on 2018/9/28.
 * </pre>
 */
public class EmployerProductItem extends EmployerServiceItem {

  @ApiModelProperty("服务项名称")
  private String itemName; // productItem名称，方便前端显示数据

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

}