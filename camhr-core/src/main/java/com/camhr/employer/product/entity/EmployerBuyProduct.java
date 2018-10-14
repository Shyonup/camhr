package com.camhr.employer.product.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;

/**
 * 企业购买服务包，方便前端传输数据
 *
 * Created by Allen on 2018/10/9.
 */
public class EmployerBuyProduct {

  @JsonProperty(access = Access.READ_ONLY)
  private long employerId; // 后台从登陆信息中获取

  @ApiModelProperty("服务Id")
  private int productId;

  @ApiModelProperty("礼品卡卡号")
  private String cardNo; // 礼品卡（优惠卡Id），C_CARD表的主键

  @ApiModelProperty("购买数量，不传就默认是1")
  @Min(1)
  private int quantity = 1; // 购买数量

  // ==================================== get / set ====================================

  @ApiModelProperty(hidden = true)
  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = Math.abs(quantity); // 必须是正数，不然，负数会导致， 购买产品扣费时 变成 恶意充值
  }

}
