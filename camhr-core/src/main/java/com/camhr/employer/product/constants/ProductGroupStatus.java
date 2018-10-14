package com.camhr.employer.product.constants;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

@ConstantMapper
public enum ProductGroupStatus {

  OK(Constants.of("PRODUCT_GROUP_STATUS", "ok", 0, "正常")),
  REMOVED(Constants.of("PRODUCT_GROUP_STATUS", "removed", 1, "停用"));

  private final Constant<Integer> constant;

  private ProductGroupStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return (Integer)this.constant.getValue();
  }

  public Constant constant() {
    return this.constant;
  }

  public static List<Integer> defaults() {
    Collection<Constant> values = Constants.of("PRODUCT_GROUP_STATUS").values();
    List<Integer> list = Lists.newArrayList();
    values.forEach((c) -> {
      if (c.getValue() != REMOVED.value()) {
        list.add((Integer)c.getValue());
      }

    });
    return list;
  }
}
