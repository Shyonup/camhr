package com.camhr.im;

public class IMProperties {

  private String appKey = "cc976734b3ffabfe736ee2195ba666ff";
  private String appSecret = "cdde1eab3448";
  private String endpoint = "https://api.netease.im/nimserver";
  private int randomLength = 10;

  public int getRandomLength() {
    return randomLength;
  }

  public void setRandomLength(int randomLength) {
    this.randomLength = randomLength;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }
}
