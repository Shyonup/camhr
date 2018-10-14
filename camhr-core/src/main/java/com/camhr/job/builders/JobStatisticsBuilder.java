package com.camhr.job.builders;

import com.camhr.job.utils.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * Created by Allen on 2018/9/25.
 */
public class JobStatisticsBuilder {

  @ApiModelProperty(hidden = true)
  private long employerId = -1; // 后台从登陆信息中获取

  // ==================================== get / set ====================================

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public Date getNow() {
    return TimeUtil.getNowDate();
  }
}
