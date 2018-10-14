package com.camhr.job.entity;

import com.camhr.job.utils.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import we.json.AsConstant;

public class EmployerJobApply extends JobApply {

  // ==== 简历信息 ====
  @ApiModelProperty(notes = "最高学历,常量：QUALIFICATION")
  @AsConstant("QUALIFICATION")
  private int qualificationId;

  @ApiModelProperty(notes = "工作年数")
  private int careerExperience;

  // ==== 个人信息 ====
  @ApiModelProperty(notes = "生日")
  private Date birthday;

  @ApiModelProperty(notes = "年龄")
  private int age;

  @ApiModelProperty(notes = "姓")
  private String firstName;

  @ApiModelProperty(notes = "名")
  private String lastName;

  @ApiModelProperty(notes = "性别 常量：SEX")
  @AsConstant("SEX")
  private int sexId;

  // ==== 职位信息 ====
  @ApiModelProperty(notes = "职位名称")
  private String jobTitle;

  // ==== 冗余字段，方便前端筛选状态,这个字段暂时先不管 ====
  @JsonIgnore
  private int showStatus;

  // ==================================== get / set ====================================

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

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
    if (birthday != null) {
      this.age = TimeUtil.countAges(birthday);
    }
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  @ApiModelProperty(hidden = true)
  public int getShowStatus() {
    return showStatus;
  }

  public void setShowStatus(int showStatus) {
    this.showStatus = showStatus;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
