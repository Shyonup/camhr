package com.camhr.employer.product.builder;

import com.camhr.constants.ServiceItem;
import com.camhr.employer.product.constants.EmployerProductItemStatus;
import com.camhr.job.constants.JobLanguage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import we.query.QueryBuilder;

/**
 * Created by Allen on 2018/9/30.
 */
public class ProductItemQueryBuilder extends QueryBuilder {

  @ApiModelProperty(hidden = true)
  private long employerId; // 后台从登陆信息中获取

  private List<Integer> status = EmployerProductItemStatus.defaults();

  private List<Integer> itemIds = ServiceItem.itemIdAboutJob();

  private Integer minAmount = 0;

  @ApiModelProperty(hidden = true)
  private JobLanguage language = JobLanguage.EN; // 后台从Controller层获取

  @ApiModelProperty(hidden = true)
  private boolean isQueryEmployerProduct; // 后台在Service层用的

  @ApiModelProperty(hidden = true)
  private boolean mustNotExpired = true; // 后台用的

  // ==================================== get / set ====================================

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public List<Integer> getStatus() {
    return status;
  }

  public void setStatus(List<Integer> status) {
    this.status = status;
  }

  public List<Integer> getItemIds() {
    return itemIds;
  }

  public void setItemIds(List<Integer> itemIds) {
    this.itemIds = itemIds;
  }

  public Integer getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(Integer minAmount) {
    this.minAmount = minAmount;
  }

  public JobLanguage getLanguage() {
    return language;
  }

  public void setLanguage(JobLanguage language) {
    this.language = language;
  }

  public boolean isQueryEmployerProduct() {
    return isQueryEmployerProduct;
  }

  public void setQueryEmployerProduct(boolean queryEmployerProduct) {
    isQueryEmployerProduct = queryEmployerProduct;
  }

  public boolean isMustNotExpired() {
    return mustNotExpired;
  }

  public void setMustNotExpired(boolean mustNotExpired) {
    this.mustNotExpired = mustNotExpired;
  }
}
