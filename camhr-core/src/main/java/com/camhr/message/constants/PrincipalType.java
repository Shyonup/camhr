package com.camhr.message.constants;

import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

@ConstantMapper
public enum PrincipalType {
  PHONE(Constants.of("PRINCIPAL_TYPE", "PHONE", 1, "手机")),
  EMAIL(Constants.of("PRINCIPAL_TYPE", "EMAIL", 2, "邮箱"));


  private final Constant<Integer> constant;

  PrincipalType(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

}
