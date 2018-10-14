package com.camhr.employer.wallet.constants;


import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.ConstantMap;
import we.lang.Constants;

/**
 * 订单状态
 */
@ConstantMapper
public enum RechargeStatus implements ConstantMap<RechargeStatus, Integer> {
  NOPAY(Constants.of("ORDER_STATUS", "NOPAY", 0, "未支付")),
  PAID(Constants.of("ORDER_STATUS", "PAID", 1, "已支付")),
  CANCEL(Constants.of("ORDER_STATUS", "PAID", 2, "已取消")),
  SERVICED_NOPAY(Constants.of("ORDER_STATUS", "SERVICED_NOPAY", 8, "已开通，未付")),
  REMOVED(Constants.of("ORDER_STATUS", "REMOVED", 9, "已删除"));

  private final Constant<Integer> constant;

  RechargeStatus(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

  public Constant<Integer> constant() {
    return this.constant;
  }

  public static List<Integer> defaults() {
    Collection<Constant> values = Constants.of("ORDER_STATUS").values();
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
    Constant c = Constants.of("ORDER_STATUS").valueOf(status);
    if (c != null) {
      lable = c.getLabel();
    }
    return lable;
  }
}
