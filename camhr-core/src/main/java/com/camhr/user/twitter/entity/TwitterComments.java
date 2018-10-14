package com.camhr.user.twitter.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;

public class TwitterComments {

  @ApiModelProperty(hidden = true)
  private long id;
  @ApiModelProperty(value = "评论者id")
  private long userId;
  @ApiModelProperty(value = "动态id")
  private long twitterId;
  @ApiModelProperty(value = "评论内容")
  @NotBlank
  @Length(max = 200)
  private String content;
  @ApiModelProperty(value = "是否匿名 常量：ANONYMOUS_STATUS")
  @AsConstant("ANONYMOUS_STATUS")
  private int anonymous;
  @ApiModelProperty(hidden = true)
  private Date createAt;
  @ApiModelProperty(hidden = true)
  private Date updateAt;

  //冗余字段
  @ApiModelProperty(value = "评论者名称")
  private String userName;
  @ApiModelProperty(value = "评论者头像")
  private String avatar;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getAnonymous() {
    return anonymous;
  }

  public void setAnonymous(int anonymous) {
    this.anonymous = anonymous;
  }

  public long getTwitterId() {
    return twitterId;
  }

  public void setTwitterId(long twitterId) {
    this.twitterId = twitterId;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }
}
