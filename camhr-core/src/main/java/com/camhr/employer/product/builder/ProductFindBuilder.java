package com.camhr.employer.product.builder;

import com.camhr.employer.product.constants.ProductStatus;
import com.camhr.job.constants.JobLanguage;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Set;

/**
 * Created by Allen on 2018/9/28.
 */
public class ProductFindBuilder {

  @ApiModelProperty(hidden = true)
  private JobLanguage language = JobLanguage.KH; // 国际化消息，后台自行获取

  private List<Integer> status = ProductStatus.defaults();

  private Set<Integer> groupIds = Sets.newHashSet();

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

  public Set<Integer> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(Set<Integer> groupIds) {
    this.groupIds = groupIds;
  }
}
