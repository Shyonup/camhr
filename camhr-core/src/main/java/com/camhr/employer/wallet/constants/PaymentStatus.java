package com.camhr.employer.wallet.constants;


import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.ConstantMap;
import we.lang.Constants;

/**
 * 流水状态
 */
@ConstantMapper
public enum PaymentStatus implements ConstantMap<PaymentStatus, Integer> {
  PRE(Constants.of("PAYMENT_STATUS", "PRE", 0, "预付单")),
  OK(Constants.of("PAYMENT_STATUS", "ok", 1, "正常")),
  FAIL(Constants.of("PAYMENT_STATUS", "fail", 8, "支付失败")),
  REMOVED(Constants.of("PAYMENT_STATUS", "removed", 9, "已删除"));

  private final Constant<Integer> constant;

  PaymentStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

  public Constant<Integer> constant() {
    return this.constant;
  }

  public static List<Integer> defaults() {
    Collection<Constant> values = Constants.of("PAYMENT_STATUS").values();
    List<Integer> list = Lists.newArrayList();
    for (Constant c : values) {
      // 去掉默认不需要查询出来的状态
      if (c.getValue() == REMOVED.value()) {
        continue;
      }
      list.add((Integer) c.getValue());
    }

    return list;
  }

  public static String getLable(int status) {
    String lable = null;
    Constant c = Constants.of("PAYMENT_STATUS").valueOf(status);
    if (c != null) {
      lable = c.getLabel();
    }
    return lable;
  }
}
