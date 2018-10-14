package com.camhr.user.twitter.constants;

import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

@ConstantMapper
public enum AnonymousStatus {

  ANONYMOUS(Constants.of("ANONYMOUS_STATUS", "anonymous", 0, "匿名")),
  NOT_ANONYMOUS(Constants.of("ANONYMOUS_STATUS", "not_anonymous", 1, "不匿名"));

  private final Constant<Integer> constant;

  AnonymousStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

}
