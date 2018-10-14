package com.camhr.employer.account.constants;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import we.lang.Constant;
import we.lang.ConstantMap;
import we.lang.Constants;

public enum TradeType implements ConstantMap<TradeType, Integer> {
  BANK(Constants.of("TRADE_TYPE", "BANK", 1, "银行转账")),
  CASH(Constants.of("TRADE_TYPE", "CASH", 2, "现金充值")),
  WING(Constants.of("TRADE_TYPE", "WING", 3, "wing")),
  PURCHASE_PRODUCTS(Constants.of("TRADE_TYPE", "PURCHASE_PRODUCTS", 4, "产品购买")),
  VAT(Constants.of("TRADE_TYPE", "VAT", 5, "VAT")),
  CANCEL_PURCHASE_PRODUCTS(Constants.of("TRADE_TYPE", "CANCEL_PURCHASE_PRODUCTS", 6, "取消购买产品")),
  ONLINE_PAY(Constants.of("TRADE_TYPE", "ONLINE_PAY", 8, "在线支付"));

  private final Constant<Integer> constant;

  TradeType(Constant<Integer> constant) {
    this.constant = constant;
  }

  public Integer value() {
    return this.constant.getValue();
  }

  public Constant<Integer> constant() {
    return this.constant;
  }

  public static List<Integer> defaults() {
    Collection<Constant> values = Constants.of("TRADE_TYPE").values();
    List<Integer> list = Lists.newArrayList();
    for (Constant c : values) {
      list.add((Integer) c.getValue());
    }

    return list;
  }
}
