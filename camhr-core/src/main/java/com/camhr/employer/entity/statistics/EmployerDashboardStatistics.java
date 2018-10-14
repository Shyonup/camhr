package com.camhr.employer.entity.statistics;

import io.swagger.annotations.ApiModelProperty;

/**
 * 公司首页统计数据
 * Created by Allen on 2018/9/26.
 */
public class EmployerDashboardStatistics {

  @ApiModelProperty("最近简历数量")
  private int recentJobApply;

  @ApiModelProperty("待面试数量")
  private int waitToInterview;

  // ==================================== get / set ====================================

  public int getRecentJobApply() {
    return recentJobApply;
  }

  public void setRecentJobApply(int recentJobApply) {
    this.recentJobApply = recentJobApply;
  }

  public int getWaitToInterview() {
    return waitToInterview;
  }

  public void setWaitToInterview(int waitToInterview) {
    this.waitToInterview = waitToInterview;
  }
}
