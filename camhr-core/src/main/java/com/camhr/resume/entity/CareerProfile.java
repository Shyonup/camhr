package com.camhr.resume.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Min;
import we.json.AsConstant;

public class CareerProfile {

  @ApiModelProperty(notes = "简历id")
  private long resumeId;

  @AsConstant("QUALIFICATION")
  @ApiModelProperty(notes = "学历,常量：QUALIFICATION")
  @Min(1)
  private int qualificationId;

  @ApiModelProperty(notes = "工作年数")
  @Min(0)
  private int careerExperience;

  @ApiModelProperty("近期职位")
  private String careerPosition;

  @ApiModelProperty("最近职业")
  @AsConstant("CATEGORY")
  private int careerCategory;

  @ApiModelProperty(notes = "最近行业")
  @AsConstant("INDUSTRIAL")
  private int careerIndustry;

  @ApiModelProperty(notes = "职业水平")
  @AsConstant("JOB_LEVEL")
  @Min(1)
  private int careerLevel;

  @ApiModelProperty(notes = "最近工资")
  private double salary;

  @ApiModelProperty(hidden = true)
  private Date updateTime;

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  public int getQualificationId() {
    return qualificationId;
  }

  public void setQualificationId(int qualificationId) {
    this.qualificationId = qualificationId;
  }

  public int getCareerExperience() {
    return careerExperience;
  }

  public void setCareerExperience(int careerExperience) {
    this.careerExperience = careerExperience;
  }

  public String getCareerPosition() {
    return careerPosition;
  }

  public void setCareerPosition(String careerPosition) {
    this.careerPosition = careerPosition;
  }

  public int getCareerCategory() {
    return careerCategory;
  }

  public void setCareerCategory(int careerCategory) {
    this.careerCategory = careerCategory;
  }

  public int getCareerIndustry() {
    return careerIndustry;
  }

  public void setCareerIndustry(int careerIndustry) {
    this.careerIndustry = careerIndustry;
  }

  public int getCareerLevel() {
    return careerLevel;
  }

  public void setCareerLevel(int careerLevel) {
    this.careerLevel = careerLevel;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
