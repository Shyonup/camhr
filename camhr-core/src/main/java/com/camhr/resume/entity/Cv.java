package com.camhr.resume.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;

public class Cv {

  private long resumeId;

  @ApiModelProperty(notes = "求职者id")
  private long seekerId;

  @ApiModelProperty(notes = "简历名称")
  private String name;  //简历名称

  @ApiModelProperty(notes = "简历完成度")
  private int completeness;

  @ApiModelProperty(notes = "上次更新时间")
  private Date updateTime;

  @ApiModelProperty(hidden = true)
  private Date createTime;

  @ApiModelProperty(hidden = true)
  private String languageFlag;

  @ApiModelProperty(notes = "培训经历")
  private String training;

  @ApiModelProperty(notes = "兴趣爱好")
  private String hobby;

  @ApiModelProperty(notes = "自我评价")
  private String description;

  @ApiModelProperty(notes = "工作性质,常量:JOBTERM ")
  @AsConstant("JOBTERM")
  private int reqJobtermId;

  @ApiModelProperty(notes = "期望职位 ")
  private String reqJobTitle;

  @AsConstant("SALARY")
  @ApiModelProperty(notes = "薪资要求， 常量：SALARY")
  private int reqSalaryId;

  private CareerProfile careerProfile = new CareerProfile();

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  public long getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(long seekerId) {
    this.seekerId = seekerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCompleteness() {
    return completeness;
  }

  public void setCompleteness(int completeness) {
    this.completeness = completeness;
  }

  @JsonFormat(pattern = "yyyy-MM-dd")
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getLanguageFlag() {
    return languageFlag;
  }

  public void setLanguageFlag(String languageFlag) {
    this.languageFlag = languageFlag;
  }

  public String getTraining() {
    return training;
  }

  public void setTraining(String training) {
    this.training = training;
  }

  public String getHobby() {
    return hobby;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getReqJobtermId() {
    return reqJobtermId;
  }

  public void setReqJobtermId(int reqJobtermId) {
    this.reqJobtermId = reqJobtermId;
  }

  public String getReqJobTitle() {
    return reqJobTitle;
  }

  public void setReqJobTitle(String reqJobTitle) {
    this.reqJobTitle = reqJobTitle;
  }

  public CareerProfile getCareerProfile() {
    return careerProfile;
  }

  public void setCareerProfile(CareerProfile careerProfile) {
    this.careerProfile = careerProfile;
  }

  public int getReqSalaryId() {
    return reqSalaryId;
  }

  public void setReqSalaryId(int reqSalaryId) {
    this.reqSalaryId = reqSalaryId;
  }
}
