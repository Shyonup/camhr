package com.camhr.job.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;

/**
 * <pre>
 * 职位置顶续费，与数据库表无关，只是方便前端传输数据而已，数据结构和发布职位一样
 *
 * Created by Allen on 2018/10/12.
 * </pre>
 */
public class TopJob extends PublishJob {

  @ApiModelProperty("服务项的使用数量，换句话说，就是要置顶多少天")
  @Min(0)
  private int quantity = 1; // 使用数量

  // ==================================== get / set ====================================

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = Math.abs(quantity); // 必须是正数
  }
}
