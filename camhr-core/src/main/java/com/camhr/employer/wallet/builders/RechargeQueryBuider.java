package com.camhr.employer.wallet.builders;

import com.camhr.employer.wallet.constants.RechargeStatus;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import we.query.QueryBuilder;

public class RechargeQueryBuider extends QueryBuilder {

  @ApiModelProperty("常量：ORDER_STATUS")
  private List<Integer> status = RechargeStatus.defaults();

  @ApiModelProperty(hidden = true)
  private long employerId;

  public List<Integer> getStatus() {
    return status;
  }

  public void setStatus(List<Integer> status) {
    this.status = status;
  }

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }
}
