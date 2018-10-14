package com.camhr.employer.wallet.constants;

public enum PaymentStatusMapping {

  OK(we.payment.PaymentStatus.SUCCESS, PaymentStatus.OK),
  FAIL(we.payment.PaymentStatus.FAILURE, PaymentStatus.FAIL);

  private final we.payment.PaymentStatus status;
  private final PaymentStatus paymentStatus;

  PaymentStatusMapping(we.payment.PaymentStatus status, PaymentStatus paymentStatus) {
    this.status = status;
    this.paymentStatus = paymentStatus;
  }

  public static PaymentStatusMapping of(we.payment.PaymentStatus status) {
    for (int i = 0; i < values().length; i++) {
      if (values()[i].status == status) {
        return values()[i];
      }
    }
    return FAIL;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

}
