package com.camhr.employer.constants;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

@ConstantMapper
public enum  EmployerStatus {

  PENDING(Constants.of("EMPLOYER_STATUS", "pending", 0, "待审核")),
  OK(Constants.of("EMPLOYER_STATUS", "ok", 1, "正常")),
  REMOVED(Constants.of("EMPLOYER_STATUS", "removed", 9, "停用"));

  private final Constant<Integer> constant;

  private EmployerStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return (Integer)this.constant.getValue();
  }

  public Constant constant() {
    return this.constant;
  }

  public static List<Integer> defaults() {
    Collection<Constant> values = Constants.of("EMPLOYER_STATUS").values();
    List<Integer> list = Lists.newArrayList();
    values.forEach((c) -> {
      if (c.getValue() != REMOVED.value()) {
        list.add((Integer)c.getValue());
      }

    });
    return list;
  }
}
