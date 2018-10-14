package com.camhr.employer.entity;

import com.camhr.job.utils.TimeUtil;
import com.camhr.resume.entity.Cv;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import we.json.AsConstant;

public class ResumeInfo extends Cv {

  //推荐功能需要的字段，出生城市，电话，邮箱都应该屏蔽
  @ApiModelProperty(value = "生日")
  private Date birthday;
  @ApiModelProperty(value = "居住地址")
  private int locationId;

  //人才搜索
  @ApiModelProperty(value = "头像地址")
  private String avatar;
  @ApiModelProperty(value = "名")
  private String firstName;
  @ApiModelProperty(value = "姓")
  private String lastName;
  @ApiModelProperty(value = "性别, 常量：SEX")
  @AsConstant("SEX")
  private int sexId;
  @ApiModelProperty(value = "工作年限")
  private int careerExperience;
  @ApiModelProperty(value = "学历,常量：QUALIFICATION")
  @AsConstant("QUALIFICATION")
  private int qualificationId;
  @ApiModelProperty(value = "当前职位，常量：JOBTERM")
  @AsConstant("JOBTERM")
  private int termId;
  @ApiModelProperty(value = "当前公司")
  private int company;
  @ApiModelProperty(value = "期望薪资，常量：SALARY")
  @AsConstant("SALARY")
  private int salaryId;
  @ApiModelProperty(value = "年龄")
  private int age;

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
    this.age = TimeUtil.countAges(birthday);
  }

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
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

  public int getSexId() {
    return sexId;
  }

  public void setSexId(int sexId) {
    this.sexId = sexId;
  }

  public int getCareerExperience() {
    return careerExperience;
  }

  public void setCareerExperience(int careerExperience) {
    this.careerExperience = careerExperience;
  }

  public int getQualificationId() {
    return qualificationId;
  }

  public void setQualificationId(int qualificationId) {
    this.qualificationId = qualificationId;
  }

  public int getTermId() {
    return termId;
  }

  public void setTermId(int termId) {
    this.termId = termId;
  }

  public int getCompany() {
    return company;
  }

  public void setCompany(int company) {
    this.company = company;
  }

  public int getSalaryId() {
    return salaryId;
  }

  public void setSalaryId(int salaryId) {
    this.salaryId = salaryId;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
