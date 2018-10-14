package com.camhr.employer.wallet.constants;


import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.ConstantMap;
import we.lang.Constants;

/**
 * 为了方便信息扩展，代码中只定义需要进行业务判断的常量，也可以定义多个常量<br/> 未定义的常量可能从数据库加载
 */
@ConstantMapper
public enum PaymentType implements ConstantMap<PaymentType, Integer> {
  COLLECT(Constants.of("PAYMENT_TYPE", "collect", 0, "收款")),
  REFUND(Constants.of("PAYMENT_TYPE", "refund", 1, "退款"));

  private final Constant<Integer> constant;

  PaymentType(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

  public Constant<Integer> constant() {
    return this.constant;
  }

  public static String getLable(int status) {
    String label = null;
    Constant c = Constants.of("PAYMENT_TYPE").valueOf(status);
    if (c != null) {
      label = c.getLabel();
    }
    return label;
  }

  /**
   * 根据名称获取value值
   * @param label
   * @return
   */
  public static int getValueByLabel(String label) {
    int result = -1;

    // 获取所有的常量
    Constant constant = Constants.of("PAYMENT_TYPE").labelOf(label);
    if (constant != null) {
      result = (int) constant.getValue();
    }

    return result;
  }
}
