package com.camhr.job.constants;

import com.google.common.collect.Lists;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

/**
 * 投递简历后，该职位的面试状态
 *
 * JobApply实体类 里面有个面试状态字段
 */
@ConstantMapper
public enum InterviewStatus {

  NONE(Constants.of("INTERVIEW_STATUS", "none", -1, "未标记")), // 没有操作数据，数据默认是-1
  NEVER(Constants.of("INTERVIEW_STATUS", "never", 0, "未面试")),
  VIEWED(Constants.of("INTERVIEW_STATUS", "viewed", 1, "已面试"));

  private final Constant<Integer> constant;

  private InterviewStatus(Constant<Integer> constant) {
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
    for (InterviewStatus status : values()) {
      list.add(status.value());
    }
    return list;
  }
}
