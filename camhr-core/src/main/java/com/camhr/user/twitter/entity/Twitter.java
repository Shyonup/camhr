package com.camhr.user.twitter.entity;

import com.camhr.user.twitter.constants.AnonymousStatus;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;

public class Twitter {

  @ApiModelProperty(hidden = true)
  private long id;
  @ApiModelProperty(value = "求职者id")
  private long userId;
  @NotBlank
  @Length(max = 200)
  @ApiModelProperty(value = "动态内容")
  private String content;
  @ApiModelProperty(value = "图片路径")
  @Size(max = 9)
  private List<String> images;
  @ApiModelProperty(value = "是否匿名 常量：ANONYMOUS_STATUS")
  @AsConstant("ANONYMOUS_STATUS")
  private int anonymous = AnonymousStatus.NOT_ANONYMOUS.value();
  @ApiModelProperty(hidden = true)
  private int likes;
  @ApiModelProperty(hidden = true)
  private int comments;
  @ApiModelProperty(hidden = true)
  private int status;  //1 未删除， 0 已删除
  @ApiModelProperty(hidden = true)
  private Date createAt;
  @ApiModelProperty(hidden = true)
  private Date updateAt;

  //冗余字段
  @ApiModelProperty(hidden = true)
  private String userName;
  @ApiModelProperty(hidden = true)
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public int getAnonymous() {
    return anonymous;
  }

  public void setAnonymous(int anonymous) {
    this.anonymous = anonymous;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
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

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
