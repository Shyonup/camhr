package com.camhr.core.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

public class ResetPassword {

  @Length(min = 4)
  @ApiModelProperty(notes = "邮箱/短信验证码")
  private String verificationCode;

  @Length(min = 6)
  @ApiModelProperty(notes = "新密码")
  private String password;

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
