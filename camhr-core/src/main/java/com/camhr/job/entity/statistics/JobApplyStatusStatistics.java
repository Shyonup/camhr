package com.camhr.job.entity.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

/**
 * 简历 - 职位 关联关系的状态统计
 *
 * Created by Allen on 2018/9/25.
 */
public class JobApplyStatusStatistics {

  @JsonIgnore
  private long jobId;

  @ApiModelProperty(readOnly = true, value = "可约聊")
  private int waitToCommunicate;

  @ApiModelProperty(readOnly = true, value = "候选人")
  private int candidates;

  @ApiModelProperty(readOnly = true, value = "待面试")
  private int waitToInterview;

  // ==================================== get / set ====================================

  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  public int getWaitToCommunicate() {
    return waitToCommunicate;
  }

  public void setWaitToCommunicate(int waitToCommunicate) {
    this.waitToCommunicate = waitToCommunicate;
  }

  public int getCandidates() {
    return candidates;
  }

  public void setCandidates(int candidates) {
    this.candidates = candidates;
  }

  public int getWaitToInterview() {
    return waitToInterview;
  }

  public void setWaitToInterview(int waitToInterview) {
    this.waitToInterview = waitToInterview;
  }
}
