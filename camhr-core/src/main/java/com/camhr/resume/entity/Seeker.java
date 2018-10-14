package com.camhr.resume.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import we.json.AsConstant;

public class Seeker {

  @ApiModelProperty(notes = "求职者id")
  private long seekerId;

  @ApiModelProperty(notes = "username")
  private String name;

  @JsonIgnore
  private String password;

  @ApiModelProperty(notes = "默认语言")
  private String language;

  @ApiModelProperty(notes = "姓")
  @NotBlank
  private String firstName;

  @ApiModelProperty(notes = "名")
  @NotBlank
  private String lastName;

  @ApiModelProperty(notes = "性别")
  @AsConstant("SEX")
  private int sexId; //性别

  @ApiModelProperty(notes = "出生年月")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date birthday; //出生年月

  @ApiModelProperty(notes = "工作开始时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date workBeginDate; //工作开始时间

  @ApiModelProperty(notes = "居住地")
  private String Address; //居住地

  @ApiModelProperty(notes = "现居住城市， 常量：LOCATION")
  @AsConstant("LOCATION")
  private int locationId; //现居住城市

  @ApiModelProperty(notes = "邮箱")
  @NotBlank
  private String email; //邮箱

  @ApiModelProperty(notes = "手机号码")
  @NotBlank
  private String mobile; //手机

  @ApiModelProperty(notes = "国家码")
  @NotBlank
  private String regionCode; //国家码

  @AsConstant("WORK_STATUS")
  @ApiModelProperty(notes = "求职状态， 常量：WORK_STATUS")
  private int workStatus;

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(long seekerId) {
    this.seekerId = seekerId;
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

  @JsonFormat(pattern = "yyyy-MM-dd")
  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @JsonFormat(pattern = "yyyy-MM-dd")
  public Date getWorkBeginDate() {
    return workBeginDate;
  }

  public void setWorkBeginDate(Date workBeginDate) {
    this.workBeginDate = workBeginDate;
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public int getWorkStatus() {
    return workStatus;
  }

  public void setWorkStatus(int workStatus) {
    this.workStatus = workStatus;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }
}
