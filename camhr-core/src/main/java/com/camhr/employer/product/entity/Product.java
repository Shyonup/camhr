package com.camhr.employer.product.entity;

import com.camhr.employer.product.constants.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;

/**
 * <pre>
 * 服务项目
 *    对应数据库 P_SERVICE 表
 *
 * Created by Allen on 2018/9/27.
 * </pre>
 */
public class Product {

  private int id;

  @Length(max = 256)
  @NotBlank
  @JsonProperty(access = Access.WRITE_ONLY) // 只写不读，读数据从name字段获取
  private String nameEn;

  @Length(max = 256)
  @JsonProperty(access = Access.WRITE_ONLY) // 只写不读，读数据从name字段获取
  private String nameZh;

  @Length(max = 256)
  @JsonProperty(access = Access.WRITE_ONLY) // 只写不读，读数据从name字段获取
  private String nameKh;

  private BigDecimal price = new BigDecimal("0");

  @AsConstant("PRODUCT_STATUS")
  private int status = ProductStatus.OK.value();

  private Date cdate;

  @Length(max = 1024)
  @JsonProperty(access = Access.WRITE_ONLY) // 只写不读，读数据从description字段获取
  private String descriptionEn;

  @Length(max = 1024)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String descriptionZh;

  @Length(max = 1024)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String descriptionKh;

  @NotNull
  private Date startDate;

  @NotNull
  private Date endDate;

  private Date udate;

  private int groupId;

  private int sortOrder = 99;

  // ==================================== 冗余字段 ====================================

  @JsonProperty(access = Access.READ_ONLY) // 只读不写
  private String name; // 国际化消息，从 nameEn/nameZh/nameKh 中取值

  @JsonProperty(access = Access.READ_ONLY) // 只读不写
  private String description; // 国际化消息，从 descriptionEn/descriptionZh/descriptionKh 中取值

  private List<ProductItem> productItems = Lists.newArrayList();

  // ==================================== get / set ====================================

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @ApiModelProperty("CreateAt，记录创建时间")
  public Date getCdate() {
    return cdate;
  }

  public void setCdate(Date cdate) {
    this.cdate = cdate;
  }

  @ApiModelProperty(hidden = true)
  public String getDescriptionEn() {
    return descriptionEn;
  }

  public void setDescriptionEn(String descriptionEn) {
    this.descriptionEn = descriptionEn;
  }

  @ApiModelProperty(hidden = true)
  public String getDescriptionZh() {
    return descriptionZh;
  }

  public void setDescriptionZh(String descriptionZh) {
    this.descriptionZh = descriptionZh;
  }

  @ApiModelProperty(hidden = true)
  public String getDescriptionKh() {
    return descriptionKh;
  }

  public void setDescriptionKh(String descriptionKh) {
    this.descriptionKh = descriptionKh;
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

  @ApiModelProperty("更新时间")
  public Date getUdate() {
    return udate;
  }

  public void setUdate(Date udate) {
    this.udate = udate;
  }

  @ApiModelProperty("外键，服务包的主键")
  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  @ApiModelProperty(hidden = true)
  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  @ApiModelProperty(readOnly = true, value = "服务 名称")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ApiModelProperty(readOnly = true, value = "详细描述")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ProductItem> getProductItems() {
    return productItems;
  }

  public void setProductItems(List<ProductItem> productItems) {
    this.productItems = productItems;
  }
}
