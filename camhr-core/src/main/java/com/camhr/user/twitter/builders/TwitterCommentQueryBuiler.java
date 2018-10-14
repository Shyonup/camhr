package com.camhr.user.twitter.builders;

import io.swagger.annotations.ApiModelProperty;
import we.query.QueryBuilder;

public class TwitterCommentQueryBuiler extends QueryBuilder {

  @ApiModelProperty(value = "动态id")
  private long twtitterId;

  public long getTwtitterId() {
    return twtitterId;
  }

  public void setTwtitterId(long twtitterId) {
    this.twtitterId = twtitterId;
  }
}
