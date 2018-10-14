package com.camhr.employer.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class EmployerRegParam {

  @NotEmpty
  @NotBlank
  @Length(min = 4, max = 30)
  private String name;//用户名

  @NotEmpty
  @NotBlank
  @Length(min = 6)
  private String password;

  @ApiModelProperty(hidden = true)
  private String language = "en";

  @NotEmpty
  @NotBlank
  private String company;//公司名

  @NotEmpty
  @NotBlank
  private String contactname;//联系人

  @NotEmpty
  @NotBlank
  @Length(min = 1, max = 20)
  private String mobile;//手机

  @NotEmpty
  @NotBlank
  @Length(min = 1, max = 20)
  private String regionCode;//区域码
  @NotEmpty
  @NotBlank
  @Length(min = 1)
  @Length(min = 1, max = 20)
  private String verificationCode;//短信验证码

  @Email
  @NotEmpty
  @Length(min = 1, max = 64)
  private String email;

  @Min(1)
  private int positionId;//职位id

  @Min(1)
  private int locationId;//省市id

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getContactname() {
    return contactname;
  }

  public void setContactname(String contactname) {
    this.contactname = contactname;
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

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }
}
