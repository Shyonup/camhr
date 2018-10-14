package com.camhr.employer.builder;

import com.camhr.job.utils.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Calendar;
import java.util.Date;
import we.json.AsConstant;
import we.query.QueryBuilder;

public class ResumeQueryBuilder extends QueryBuilder {

  @ApiModelProperty("期望工作地点")
  private int expectLocationId;
  @ApiModelProperty("最小年龄")
  private Integer fromAge;
  @ApiModelProperty("最大年龄")
  private Integer toAge;
  @ApiModelProperty("关键词")
  private String jobTitle;  //关键词怎么匹配
  @ApiModelProperty("性别，常量：SEX")
  @AsConstant("SEX")
  private int sexId;
  @ApiModelProperty("学历，常量：QUALIFICATION")
  private int qualificationId;
  @ApiModelProperty("投递时间")
  private Integer deliverTime;
  @ApiModelProperty("现居住地址")
  private int liveLocationId;
  @ApiModelProperty("简历来源：用户投递 0，企业查找 1,全部的话传-1 常量两：JOB_APPLY_TYPE")
  private int applyType;
  @ApiModelProperty("职位id")
  private long jobId;
  @ApiModelProperty("最低工作年限")
  private Integer fromExperienceYear;
  @ApiModelProperty("最高工作年限")
  private Integer toExperienceYear;
  @ApiModelProperty(hidden = true)
  private long employerId;
  @ApiModelProperty(hidden = true)
  private Date fromBirthday; //用于转换年龄与生日
  @ApiModelProperty(hidden = true)
  private Date toBirthday;  //用于转换年龄与生日
  @ApiModelProperty(hidden = true)
  private Date jobApplyCreateDate;  //用于转换投递时间
  @ApiModelProperty(hidden = true)
  private Boolean userDelete = false; // 用户是否删除JobApply消息，默认不查询已删除的消息

  @ApiModelProperty(hidden = true)
  private Boolean employerDelete = false; // 企业是否删除JobApply消息，默认不查询已删除的消息

  public int getExpectLocationId() {
    return expectLocationId;
  }

  public void setExpectLocationId(int expectLocationId) {
    this.expectLocationId = expectLocationId;
  }

  public int getLiveLocationId() {
    return liveLocationId;
  }

  public void setLiveLocationId(int liveLocationId) {
    this.liveLocationId = liveLocationId;
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

  public Integer getFromAge() {
    return fromAge;
  }

  public void setFromAge(Integer fromAge) {
    this.fromAge = fromAge;
    if (fromAge != null) {
      this.toBirthday = TimeUtil.countBirthday(fromAge);
    }
  }

  public Integer getToAge() {
    return toAge;
  }

  public void setToAge(Integer toAge) {
    this.toAge = toAge;
    if (toAge != null) {
      this.fromBirthday = TimeUtil.countBirthday(toAge);
    }
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle.toLowerCase();
  }

  public int getSexId() {
    return sexId;
  }

  public void setSexId(int sexId) {
    this.sexId = sexId;
  }

  public int getQualificationId() {
    return qualificationId;
  }

  public void setQualificationId(int qualificationId) {
    this.qualificationId = qualificationId;
  }

  public Integer getDeliverTime() {
    return deliverTime;
  }

  public void setDeliverTime(Integer deliverTime) {
    this.deliverTime = deliverTime;
    if (deliverTime != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, 0 - deliverTime);
      this.jobApplyCreateDate = calendar.getTime();
    }
  }

  public int getApplyType() {
    return applyType;
  }

  public void setApplyType(int applyType) {
    this.applyType = applyType;
  }

  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  public Integer getFromExperienceYear() {
    return fromExperienceYear;
  }

  public void setFromExperienceYear(Integer fromExperienceYear) {
    this.fromExperienceYear = fromExperienceYear;
  }

  public Integer getToExperienceYear() {
    return toExperienceYear;
  }

  public void setToExperienceYear(Integer toExperienceYear) {
    this.toExperienceYear = toExperienceYear;
  }

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public Date getFromBirthday() {
    return fromBirthday;
  }

  public void setFromBirthday(Date fromBirthday) {
    this.fromBirthday = fromBirthday;
  }

  public Date getToBirthday() {
    return toBirthday;
  }

  public void setToBirthday(Date toBirthday) {
    this.toBirthday = toBirthday;
  }

  public Date getJobApplyCreateDate() {
    return jobApplyCreateDate;
  }

  public void setJobApplyCreateDate(Date jobApplyCreateDate) {
    this.jobApplyCreateDate = jobApplyCreateDate;
  }
}
