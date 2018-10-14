package com.camhr.employer.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *   企业购买的服务包
 *
 *   1. 企业购买的服务包（EmployerProduct）与 企业购买的服务项（EmployerProductItem）的区别
 *      a) 企业在UI页面 购买 服务时，买的Product。一个Product里面，有多个ProductItem
 *      b) 按道理，企业 购买一个Product，会产生 一条【EmployerProduct】数据 和 多条【EmployerProductItem】数据
 *      c) 但是，旧的数据库表设计，只保存了 多条【EmployerProductItem】数据。
 *      d) 现在UI有个页面，需要把 企业购买过的Product给查出来。
 *
 * Created by Allen on 2018/10/11.
 * </pre>
 */
public class EmployerProduct extends EmployerServiceItem {

  private BigDecimal productPrice = new BigDecimal("0"); // 价格

  private List<EmployerProductItem> employerProductItems; // 一个Product下面有多个ProductItem

  private String productDescription = ""; // Product描述

  @ApiModelProperty("服务名称")
  private String productName = "";

  @ApiModelProperty("用户在购买服务包时，在界面输入的购买数量")
  private int productAmount;

  // ==================================== get / set ====================================

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  public List<EmployerProductItem> getEmployerProductItems() {
    return employerProductItems;
  }

  public void setEmployerProductItems(
      List<EmployerProductItem> employerProductItems) {
    this.employerProductItems = employerProductItems;
  }

  public String getProductDescription() {
    return productDescription;
  }

  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  @ApiModelProperty(readOnly = true)
  public int getProductAmount() {
    return productAmount;
  }

  public void setProductAmount(int productAmount) {
    this.productAmount = productAmount;
  }

  // ----------- override -----------
  @Override
  @JsonProperty(access = Access.WRITE_ONLY) // 这个不需要显示给前端
  public int getItemId() {
    return super.getItemId();
  }

  @Override
  @JsonProperty(access = Access.WRITE_ONLY) // 这个不需要显示给前端，使用productAmount字段
  public int getAmount() {
    return super.getAmount();
  }
}
