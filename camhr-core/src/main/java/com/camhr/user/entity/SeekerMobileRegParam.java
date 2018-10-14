package com.camhr.user.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class SeekerMobileRegParam {

  @NotEmpty
  @NotBlank
  @Length(min = 4, max = 30)
  private String mobile;//用户名
  @NotEmpty
  @NotBlank
  @Length(min = 1)
  private String regionCode;
  @NotEmpty
  @NotBlank
  @Length(min = 6)
  private String password;
  @NotEmpty
  @NotBlank
  @Length(min = 1)
  private String verificationCode;
  @ApiModelProperty(hidden = true)
  private String language = "en";


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
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

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
