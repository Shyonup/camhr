package com.camhr.resume.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.format.annotation.DateTimeFormat;
import we.json.AsConstant;

public class Education {

  @ApiModelProperty(notes = "教育经历id")
  private long eduId;

  @ApiModelProperty(notes = "简历id")
  private long resumeId; //简历id

  @ApiModelProperty(notes = "教育经历开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date fromDate; //教育经历开始时间

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(notes = "教育经历结束时间")
  private Date toDate;  //教育经历结束时间

  @NotBlank
  @ApiModelProperty(notes = "学校名称")
  private String name;  //学校名称

  @ApiModelProperty(notes = "专业")
  @NotBlank
  private String major;  //专业

  @AsConstant("QUALIFICATION")
  @ApiModelProperty(notes = "学历,常量：QUALIFICATION")
  private int qualificationId;  //学历

  @ApiModelProperty(hidden = true)
  private String description;  //

  public long getEduId() {
    return eduId;
  }

  public void setEduId(long eduId) {
    this.eduId = eduId;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public int getQualificationId() {
    return qualificationId;
  }

  public void setQualificationId(int qualificationId) {
    this.qualificationId = qualificationId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
