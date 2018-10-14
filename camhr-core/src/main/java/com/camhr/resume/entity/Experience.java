package com.camhr.resume.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
public class Experience {

  private long experienceId;
  @ApiModelProperty(notes = "简历id")
  private long resumeId;  //简历id

  @ApiModelProperty(notes = "开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date fromDate;  //开始时间

  @ApiModelProperty(notes = "结束时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date toDate;  //结束时间

  @ApiModelProperty(notes = "公司名称")
  @NotBlank
  private String company;  //公司名称

  @ApiModelProperty(notes = "职位")
  @NotBlank
  private String title;  //职位

  @ApiModelProperty(notes = "工作描述")
  private String description;  //工作描述

  public long getExperienceId() {
    return experienceId;
  }

  public void setExperienceId(long experienceId) {
    this.experienceId = experienceId;
  }

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  @JsonFormat(pattern = "yyyy-MM-dd")
  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  @JsonFormat(pattern = "yyyy-MM-dd")
  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
