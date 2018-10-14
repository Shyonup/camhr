package com.camhr.job.entity;

import com.camhr.job.constants.InterviewStatus;
import com.camhr.job.constants.JobApplyStatus;
import com.camhr.job.constants.JobApplyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;

/**
 * <pre>
 * 职位 - 简历的关联关系，比如：用户投简历
 * 对应：U_JOB_APPLY 表
 *
 * Created by Allen on 2018/9/19.
 * </pre>
 */
public class JobApply {

  @JsonProperty(access = Access.READ_ONLY)
  private long id;

  @JsonProperty(access = Access.READ_ONLY)
  private long seekerId; // 求职者的userId

  private long jobId;

  private Long letterId; // 外键、介绍信

  private long cvId; // 简历Id

  @JsonProperty(access = Access.READ_ONLY)
  @AsConstant("JOB_APPLY_STATUS")
  private int status = JobApplyStatus.APPLIED.value();

  private Integer commentId; // 外键，常量：COMMENT，评价结果

  @JsonProperty(access = Access.READ_ONLY)
  private boolean userdelete; // 求职者是否删除记录

  @JsonProperty(access = Access.READ_ONLY)
  private boolean employerdelete; // 企业是否删除记录

  @Length(max = 2048)
  private String commentNote; // 评价内容

  @JsonProperty(access = Access.READ_ONLY)
  @AsConstant("JOB_APPLY_TYPE")
  private int applyType = JobApplyType.USER_APPLY.value(); // 0-用户申请，1-企业查找

  @JsonProperty(access = Access.READ_ONLY)
  private Date applydate;

  @JsonProperty(access = Access.READ_ONLY)
  private long employerId;

  @JsonProperty(access = Access.READ_ONLY)
  private Date expdate; // 过期时间

  @JsonProperty(access = Access.READ_ONLY)
  @ApiModelProperty("时间：面试")
  private Date actionTime; // 时间：面试、入职

  @Length(max = 512)
  @ApiModelProperty("地址：面试")
  private String actionAddress; // 地址：面试、入职

  @Length(max = 64)
  @ApiModelProperty("面试人")
  private String actionConact; // 面试人，联系人
  @ApiModelProperty("面试电话")
  private String actionMobile; //面试电话

  @JsonProperty(access = Access.READ_ONLY)
  private boolean seekerRead; // 已读未读

  @JsonProperty(access = Access.READ_ONLY)
  private boolean employerRead; // 已读未读

  @Length(max = 2048)
  private String feedbackInfo; // 求职者拒绝面试的反馈

  @AsConstant("INTERVIEW_STATUS")
  private int interviewStatus = InterviewStatus.NONE.value(); // 是否已经面试，null表示 不表态，别人没更新。

  // ==================================== get / set ====================================
  @ApiModelProperty(readOnly = true, value = "主键")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @ApiModelProperty(readOnly = true, value = "求职者的userId")
  public long getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(long seekerId) {
    this.seekerId = seekerId;
  }

  @ApiModelProperty(value = "职位Id", readOnly = true)
  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  @ApiModelProperty(readOnly = true, value = "外键，介绍信Id")
  public Long getLetterId() {
    return letterId;
  }

  public void setLetterId(Long letterId) {
    this.letterId = letterId;
  }

  @ApiModelProperty("简历Id")
  public long getCvId() {
    return cvId;
  }

  public void setCvId(long cvId) {
    this.cvId = cvId;
  }

  @ApiModelProperty(readOnly = true, value = "常量：JOB_APPLY_STATUS")
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @ApiModelProperty(readOnly = true, value = "评价，外键，常量：COMMENT")
  public Integer getCommentId() {
    return commentId;
  }

  public void setCommentId(Integer commentId) {
    this.commentId = commentId;
  }

  @ApiModelProperty(readOnly = true, value = "用户是否已经删除记录")
  public boolean isUserdelete() {
    return userdelete;
  }

  public void setUserdelete(boolean userdelete) {
    this.userdelete = userdelete;
  }

  @ApiModelProperty(readOnly = true, value = "企业是否删除记录")
  public boolean isEmployerdelete() {
    return employerdelete;
  }

  public void setEmployerdelete(boolean employerdelete) {
    this.employerdelete = employerdelete;
  }

  @ApiModelProperty(readOnly = true, value = "评价内容")
  public String getCommentNote() {
    return commentNote;
  }

  public void setCommentNote(String commentNote) {
    this.commentNote = commentNote;
  }

  @ApiModelProperty(readOnly = true, value = "常量：JOB_APPLY_TYPE，用户自主申请，还是企业主动邀请面试")
  public int getApplyType() {
    return applyType;
  }

  public void setApplyType(int applyType) {
    this.applyType = applyType;
  }

  @ApiModelProperty(readOnly = true, value = "邀请面试、投递简历的时间")
  public Date getApplydate() {
    return applydate;
  }

  public void setApplydate(Date applydate) {
    this.applydate = applydate;
  }

  @ApiModelProperty(readOnly = true, value = "公司Id")
  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  @ApiModelProperty(readOnly = true, value = "有效时间")
  public Date getExpdate() {
    return expdate;
  }

  public void setExpdate(Date expdate) {
    this.expdate = expdate;
  }

  @ApiModelProperty(readOnly = true, value = "时间：面试、入职")
  public Date getActionTime() {
    return actionTime;
  }

  public void setActionTime(Date actionTime) {
    this.actionTime = actionTime;
  }

  @ApiModelProperty(readOnly = true, value = "地址：面试、入职")
  public String getActionAddress() {
    return actionAddress;
  }

  public void setActionAddress(String actionAddress) {
    this.actionAddress = actionAddress;
  }

  @ApiModelProperty(readOnly = true, value = "面试人，联系人")
  public String getActionConact() {
    return actionConact;
  }

  public void setActionConact(String actionConact) {
    this.actionConact = actionConact;
  }

  @ApiModelProperty(readOnly = true, value = "用户 已读未读")
  public boolean isSeekerRead() {
    return seekerRead;
  }

  public void setSeekerRead(boolean seekerRead) {
    this.seekerRead = seekerRead;
  }

  @ApiModelProperty(readOnly = true, value = "企业已读未读")
  public boolean isEmployerRead() {
    return employerRead;
  }

  public void setEmployerRead(boolean employerRead) {
    this.employerRead = employerRead;
  }

  @ApiModelProperty(readOnly = true, value = "求职者拒绝面试的反馈")
  public String getFeedbackInfo() {
    return feedbackInfo;
  }

  public void setFeedbackInfo(String feedbackInfo) {
    this.feedbackInfo = feedbackInfo;
  }

  @ApiModelProperty(readOnly = true, value = "是否已经面试")
  public int getInterviewStatus() {
    return interviewStatus;
  }

  public void setInterviewStatus(int interviewStatus) {
    this.interviewStatus = interviewStatus;
  }

  public String getActionMobile() {
    return actionMobile;
  }

  public void setActionMobile(String actionMobile) {
    this.actionMobile = actionMobile;
  }
}
