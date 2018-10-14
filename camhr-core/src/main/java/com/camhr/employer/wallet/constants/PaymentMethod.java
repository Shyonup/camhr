package com.camhr.employer.wallet.constants;


import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.ConstantMap;
import we.lang.Constants;

/**
 * 为了方便信息扩展，代码中只定义需要进行业务判断的常量，也可以定义多个常量<br/> 未定义的常量可能从数据库加载
 */
@ConstantMapper
public enum PaymentMethod implements ConstantMap<PaymentMethod, Integer> {
  BANK(Constants.of("PAYMENT_METHOD", "bank", 1, "银行转账")),
  CASH(Constants.of("PAYMENT_METHOD", "cash", 2, "现金")),
  CHEQUE(Constants.of("PAYMENT_METHOD", "cheque", 3, "支票")),
  ALIPAY(Constants.of("PAYMENT_METHOD", "alipay", 4, "支付宝")),
  WEIXIN(Constants.of("PAYMENT_METHOD", "weixin", 5, "微信")),
  SMART_LUY(Constants.of("PAYMENT_METHOD", "smart_luy", 8, "smart_luy")),
  OTHERS(Constants.of("PAYMENT_METHOD", "others", 6, "其他"));

  private final Constant<Integer> constant;

  PaymentMethod(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

  public Constant<Integer> constant() {
    return this.constant;
  }

  public static String getLable(int status) {
    String lable = null;
    Constant c = Constants.of("PAYMENT_METHOD").valueOf(status);
    if (c != null) {
      lable = c.getLabel();
    }
    return lable;
  }

  /**
   * 根据名称获取value值
   * @param label
   * @return
   */
  public static int getValueByLabel(String label) {
    int result = -1;

    // 获取所有的常量
    Constant constant = Constants.of("PAYMENT_METHOD").labelOf(label);
    if (constant != null) {
      result = (int) constant.getValue();
    }

    return result;
  }

}
