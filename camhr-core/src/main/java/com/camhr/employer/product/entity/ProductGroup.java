package com.camhr.employer.product.entity;

import com.camhr.employer.product.constants.ProductGroupStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import we.json.AsConstant;

/**
 * <pre>
 * 服务包
 *    对应数据库 P_SERVICE_GROUP 表
 *
 * Created by Allen on 2018/9/27.
 * </pre>
 */
public class ProductGroup {

  @JsonProperty(access = Access.READ_ONLY)
  private int id;

  @AsConstant("PRODUCT_GROUP_STATUS")
  private int status = ProductGroupStatus.OK.value();

  @JsonIgnore
  private String nameEn;

  @JsonIgnore
  private String nameZh;

  @JsonIgnore
  private String nameKh;

  private int sortOrder = 99; // 排序，数值越大，排越后

  // ========================= 冗余字段，方便前端显示数据 =========================

  @ApiModelProperty("服务包名称")
  private String name;

  @ApiModelProperty("服务包 下面包含的服务")
  private List<Product> products = Lists.newArrayList();

  // ==================================== get / set ====================================

  @ApiModelProperty(readOnly = true)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @ApiModelProperty(hidden = true)
  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  @ApiModelProperty(hidden = true)
  public String getNameZh() {
    return nameZh;
  }

  @ApiModelProperty(hidden = true)
  public void setNameZh(String nameZh) {
    this.nameZh = nameZh;
  }

  @ApiModelProperty(hidden = true)
  public String getNameKh() {
    return nameKh;
  }

  public void setNameKh(String nameKh) {
    this.nameKh = nameKh;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
