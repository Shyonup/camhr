package com.camhr.user.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class SeekerRegParam {

  @NotEmpty
  @NotBlank
  @Length(min = 6)
  private String password;
  @ApiModelProperty(hidden = true)
  private String language = "en";
  @Email
  @NotEmpty
  private String email;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
