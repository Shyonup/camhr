package com.camhr.employer.product.builder;

import com.camhr.job.constants.JobLanguage;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * Created by Allen on 2018/10/12.
 */
public class ProductItemFindBuilder {

  @ApiModelProperty(hidden = true)
  private JobLanguage language = JobLanguage.EN; // 后台从Controller层获取

  private List<Long> billNos = Lists.newArrayList();

  // ==================================== get / set ====================================

  public JobLanguage getLanguage() {
    return language;
  }

  public void setLanguage(JobLanguage language) {
    this.language = language;
  }

  public List<Long> getBillNos() {
    return billNos;
  }

  public void setBillNos(List<Long> billNos) {
    this.billNos = billNos;
  }
}
