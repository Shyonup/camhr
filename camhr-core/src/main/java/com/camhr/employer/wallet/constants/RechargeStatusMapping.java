package com.camhr.employer.wallet.constants;

public enum RechargeStatusMapping {

  OK(we.payment.PaymentStatus.SUCCESS, RechargeStatus.PAID),
  FAIL(we.payment.PaymentStatus.FAILURE, RechargeStatus.NOPAY);

  private final we.payment.PaymentStatus status;
  private final RechargeStatus rechargeStatus;

  RechargeStatusMapping(we.payment.PaymentStatus status, RechargeStatus rechargeStatus) {
    this.status = status;
    this.rechargeStatus = rechargeStatus;
  }

  public static RechargeStatusMapping of(we.payment.PaymentStatus status) {
    for (int i = 0; i < values().length; i++) {
      if (values()[i].status == status) {
        return values()[i];
      }
    }
    return FAIL;
  }

  public RechargeStatus getRechargeStatus() {
    return rechargeStatus;
  }

}
