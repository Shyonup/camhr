package com.camhr.user.twitter.constants;

import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

@ConstantMapper
public enum TwitterCommentsStatus {

  DELETED(Constants.of("TWITTER_COMMENTS_STATUS", "DELETED", 0, "已删除")),
  NORMAL(Constants.of("TWITTER_COMMENTS_STATUS", "NORMAL", 1, "正常"));

  private final Constant<Integer> constant;

  TwitterCommentsStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

}
