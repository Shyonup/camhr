package com.camhr.job.constants;

import com.google.common.collect.Lists;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;
@ConstantMapper
public enum JobApplyType {

  USER_APPLY(Constants.of("JOB_APPLY_TYPE", "user_apply", 0, "用户申请")),
  EMPLOYER_FIND(Constants.of("JOB_APPLY_TYPE", "employer_find", 1, "企业查找"));

  private final Constant<Integer> constant;

  private JobApplyType(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return (Integer)this.constant.getValue();
  }

  public Constant constant() {
    return this.constant;
  }

  private static List<Integer> result;

  public static List<Integer> allTypes() {
    if (result == null) {
      result = Lists.newArrayList();
      JobApplyType[] values = values();
      for (JobApplyType value : values) {
        result.add(value.value());
      }
    }
    return result;
  }

}
