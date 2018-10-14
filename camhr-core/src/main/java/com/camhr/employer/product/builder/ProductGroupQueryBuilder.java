package com.camhr.employer.product.builder;

import com.camhr.employer.product.constants.ProductGroupStatus;
import com.camhr.job.constants.JobLanguage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import we.query.QueryBuilder;

/**
 * Created by Allen on 2018/9/27.
 */
public class ProductGroupQueryBuilder extends QueryBuilder {

  @ApiModelProperty(hidden = true)
  private JobLanguage language = JobLanguage.KH; // 国际化消息，后台自行获取

  private List<Integer> status = ProductGroupStatus.defaults();

  // ==================================== get / set ====================================


  public JobLanguage getLanguage() {
    return language;
  }

  public void setLanguage(JobLanguage language) {
    this.language = language;
  }

  public List<Integer> getStatus() {
    return status;
  }

  public void setStatus(List<Integer> status) {
    this.status = status;
  }
}
