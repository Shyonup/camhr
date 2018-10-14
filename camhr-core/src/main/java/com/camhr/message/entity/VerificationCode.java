package com.camhr.message.entity;

import com.camhr.message.constants.PrincipalType;

public class VerificationCode {

  private String principal;
  private String code;
  private int type = PrincipalType.PHONE.value();
  private boolean validated;
  private int expiredAt;
  private int lastSendAt;
  private int createAt;
  private int updateAt;

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public boolean isValidated() {
    return validated;
  }

  public void setValidated(boolean validated) {
    this.validated = validated;
  }

  public int getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(int expiredAt) {
    this.expiredAt = expiredAt;
  }

  public int getLastSendAt() {
    return lastSendAt;
  }

  public void setLastSendAt(int lastSendAt) {
    this.lastSendAt = lastSendAt;
  }

  public int getCreateAt() {
    return createAt;
  }

  public void setCreateAt(int createAt) {
    this.createAt = createAt;
  }

  public int getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(int updateAt) {
    this.updateAt = updateAt;
  }
}
