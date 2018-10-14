package com.camhr.employer.constants;

import com.camhr.job.constants.JobApplyStatus;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

/**
 * 企业端筛选简历
 */
@ConstantMapper
public enum ScreenResumes {

  APPLIED(Constants.of("SCREEN_RESUMES", "applied",
      JobApplyStatus.APPLIED.value(), "待筛选")),

  WAIT_TO_COMMUNICATE(Constants.of("SCREEN_RESUMES", "wait_to_communicate",
      JobApplyStatus.WAIT_TO_COMMUNICATE.value(), "待沟通")),

  INVITED(Constants.of("SCREEN_RESUMES", "invited",
      JobApplyStatus.INVITED.value(), "邀请面试")),

  INCOMPATIBLE(Constants.of("SCREEN_RESUMES", "incompatible",
      JobApplyStatus.INCOMPATIBLE.value(), "不合适"));

  private final Constant<Integer> constant;


  private ScreenResumes(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return (Integer)this.constant.getValue();
  }

  public String code() {
    return this.constant.getCode().toLowerCase();
  }

  public Constant constant() {
    return this.constant;
  }
}
