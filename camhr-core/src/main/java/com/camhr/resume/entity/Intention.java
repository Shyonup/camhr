package com.camhr.resume.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import we.json.AsConstant;

public class Intention {

  @ApiModelProperty(hidden = true)
  private long id;

  @ApiModelProperty(notes = "简历id")
  private long resumeId;

  @ApiModelProperty(hidden = true)
  private long seekerId;

  @ApiModelProperty(notes = "期望行业， 常量：INDUSTRIAL")
  @Size(min = 1)
  @AsConstant("INDUSTRIAL")
  private List<Integer> industryIds;

  @ApiModelProperty(notes = "期望职位")
  @Size(min = 1)
  @AsConstant("CATEGORY")
  private List<Integer> categoryIds;

  @ApiModelProperty(notes = "工作城市")
  @Size(min = 1)
  @AsConstant("LOCATION")
  private List<Integer> locationIds;

  @AsConstant("JOBTERM")
  @ApiModelProperty(notes = "工作性质， 常量：JOBTERM")
  private int jobTerm;

  @AsConstant("WORK_STATUS")
  @ApiModelProperty(notes = "求职状态， 常量：WORK_STATUS")
  private int workStatus;

  @AsConstant("SALARY")
  @ApiModelProperty(notes = "薪资要求， 常量：SALARY")
  private int salary;

  @ApiModelProperty(notes = "期望职位 ")
  @NotBlank
  private String reqJobTitle;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  public List<Integer> getIndustryIds() {
    return industryIds;
  }

  public void setIndustryIds(List<Integer> industryIds) {
    this.industryIds = industryIds;
  }

  public List<Integer> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<Integer> categoryIds) {
    this.categoryIds = categoryIds;
  }

  public List<Integer> getLocationIds() {
    return locationIds;
  }

  public void setLocationIds(List<Integer> locationIds) {
    this.locationIds = locationIds;
  }

  public int getJobTerm() {
    return jobTerm;
  }

  public void setJobTerm(int jobTerm) {
    this.jobTerm = jobTerm;
  }

  public int getWorkStatus() {
    return workStatus;
  }

  public void setWorkStatus(int workStatus) {
    this.workStatus = workStatus;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public String getReqJobTitle() {
    return reqJobTitle;
  }

  public void setReqJobTitle(String reqJobTitle) {
    this.reqJobTitle = reqJobTitle;
  }

  public long getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(long seekerId) {
    this.seekerId = seekerId;
  }


}
