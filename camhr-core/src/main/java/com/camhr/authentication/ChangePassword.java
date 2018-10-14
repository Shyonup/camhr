package com.camhr.authentication;

import org.hibernate.validator.constraints.Length;

public class ChangePassword {

  @Length(min = 6)
  private String oldPassword;

  @Length(min = 6)
  private String newPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
