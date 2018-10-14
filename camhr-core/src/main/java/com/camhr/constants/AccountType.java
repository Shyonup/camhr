package com.camhr.constants;

import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

@ConstantMapper
public enum AccountType {
  EMPLOYER(Constants.of("ACCOUNT_TYPE", "EMPLOYER", 0, "雇主")),
  SEEKER(Constants.of("ACCOUNT_TYPE", "COLLECTION", 1, "求职者"));

  private final Constant<Integer> constant;

  AccountType(Constant<Integer> constant) {
    this.constant = constant;
  }

  public int value() {
    return this.constant.getValue();
  }

  public Constant constant() {
    return this.constant;
  }
}
