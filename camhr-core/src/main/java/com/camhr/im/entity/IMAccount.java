package com.camhr.im.entity;

import com.camhr.constants.AccountType;
import java.util.HashMap;
import java.util.Map;
import we.json.AsConstant;

public class IMAccount {

  /**
   * 云通信ID
   */
  private String accessId;

  /**
   * 云通信账号token
   */
  private String token;
  /**
   * 平台用户
   */
  private String account;
  /**
   * 平台账号类型：0，企业用户；1，求职用户
   */
  @AsConstant("ACCOUNT_TYPE")
  private int type = AccountType.EMPLOYER.value();
  /**
   * 创建时间
   */
  private int createAt;

  //------ im 账号额外信息，不在数据库表字段里
  private String nickname;//用户昵称
  private String icon;//用户头像
  private Map<String, Object> extra = new HashMap<>();//扩展数据

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Map<String, Object> getExtra() {
    return extra;
  }

  public void setExtra(Map<String, Object> extra) {
    this.extra = extra;
  }

  public String getAccessId() {
    return accessId;
  }

  public void setAccessId(String accessId) {
    this.accessId = accessId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getCreateAt() {
    return createAt;
  }

  public void setCreateAt(int createAt) {
    this.createAt = createAt;
  }
}
