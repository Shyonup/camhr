package com.camhr.job.builders;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import we.query.QueryBuilder;

/**
 * Created by Allen on 2018/9/19.
 */
public class JobApplyQueryBuilder extends QueryBuilder {

  @ApiModelProperty(hidden = true)
  private long seekerId = -1; // userId后台从登录信息中获取

  @ApiModelProperty(hidden = true)
  private long employerId = -1; // 后台从登录信息中获取

  @ApiModelProperty("常量，JOB_APPLY_STATUS")
  private List<Integer> status = Lists.newArrayList();

  @ApiModelProperty("0-用户自行投递，1-企业主动邀请面试"
      + "\r\n常量：JOB_APPLY_TYPE，职位投递的类型：是企业主动邀请面试的，还是用户自行投递的")
  private Integer jobApplyType; // 常量：JOB_APPLY_TYPE

  @ApiModelProperty("面试状态")
  private int interviewStatus = -2; // 默认值必须小于-1，因为-1也是一种状态

  @ApiModelProperty("面试 开始时间")
  private Date actionStartTime;

  @ApiModelProperty("面试 结束时间")
  private Date actionEndTime;

  @ApiModelProperty("用户投递简历（或企业邀请面试） 开始时间")
  private Date applyStartTime;

  @ApiModelProperty("用户投递简历（或企业邀请面试） 结束时间")
  private Date applyEndTime;

  @ApiModelProperty(hidden = true)
  private Boolean userDelete = false; // 用户是否删除JobApply消息，默认不查询已删除的消息

  @ApiModelProperty(hidden = true)
  private Boolean employerDelete = false; // 企业是否删除JobApply消息，默认不查询已删除的消息

  // ==================================== get / set ====================================

  public long getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(long seekerId) {
    this.seekerId = seekerId;
  }

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

  public Integer getJobApplyType() {
    return jobApplyType;
  }

  public void setJobApplyType(Integer jobApplyType) {
    this.jobApplyType = jobApplyType;
  }

  public int getInterviewStatus() {
    return interviewStatus;
  }

  public void setInterviewStatus(int interviewStatus) {
    this.interviewStatus = interviewStatus;
  }

  public Date getActionStartTime() {
    return actionStartTime;
  }

  public void setActionStartTime(Date actionStartTime) {
    this.actionStartTime = actionStartTime;
  }

  public Date getActionEndTime() {
    return actionEndTime;
  }

  public void setActionEndTime(Date actionEndTime) {
    this.actionEndTime = actionEndTime;
  }

  public Date getApplyStartTime() {
    return applyStartTime;
  }

  public void setApplyStartTime(Date applyStartTime) {
    this.applyStartTime = applyStartTime;
  }

  public Date getApplyEndTime() {
    return applyEndTime;
  }

  public void setApplyEndTime(Date applyEndTime) {
    this.applyEndTime = applyEndTime;
  }

  public Boolean getUserDelete() {
    return userDelete;
  }

  public void setUserDelete(Boolean userDelete) {
    this.userDelete = userDelete;
  }

  public Boolean getEmployerDelete() {
    return employerDelete;
  }

  public void setEmployerDelete(Boolean employerDelete) {
    this.employerDelete = employerDelete;
  }
}
