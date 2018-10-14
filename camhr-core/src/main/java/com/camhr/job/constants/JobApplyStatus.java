package com.camhr.job.constants;

import com.google.common.collect.Lists;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;
@ConstantMapper
public enum JobApplyStatus {


  WAIT_TO_COMMUNICATE(Constants.of("JOB_APPLY_STATUS", "wait_to_communicate", 0, "待沟通")),
  APPLIED(Constants.of("JOB_APPLY_STATUS", "applied", 1, "已申请")),
  VIEWED(Constants.of("JOB_APPLY_STATUS", "viewed", 2, "已查看")),
  INVITED(Constants.of("JOB_APPLY_STATUS", "invited", 3, "邀请面试")),
  AGREED_INTERVIEW(Constants.of("JOB_APPLY_STATUS", "agreed_interview", 4, "接受面试")),
  REJECTED_INTERVIEW(Constants.of("JOB_APPLY_STATUS", "rejected_interview", 5, "放弃面试")),
  ON_HOLD(Constants.of("JOB_APPLY_STATUS", "on_hold", 6, "保留")),
  TO_BE_OFFERED(Constants.of("JOB_APPLY_STATUS", "to_be_offered", 7, "入职邀请")),
  OFFER_ACCEPTED(Constants.of("JOB_APPLY_STATUS", "offer_accepted", 8, "同意入职")),
  OFFER_WITHDRAWN(Constants.of("JOB_APPLY_STATUS", "offer_withdrawn", 9, "拒绝入职")),
  REJECTED_HIRE(Constants.of("JOB_APPLY_STATUS", "rejected_hire", 10, "拒绝聘用")),
  HIRED(Constants.of("JOB_APPLY_STATUS", "hired", 11, "已入职")),
  INCOMPATIBLE(Constants.of("JOB_APPLY_STATUS", "incompatible", 12, "不合适"));
  private final Constant<Integer> constant;

  private JobApplyStatus(Constant<Integer> constant) {
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

  public static List<Integer> allStatus() {
    List<Integer> list = Lists.newArrayList();
    for (JobApplyStatus status : values()) {
      list.add(status.value());
    }
    return list;
  }
}
