package com.camhr.employer.account.builder;

import com.camhr.employer.account.constants.TradeType;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import we.query.QueryBuilder;

public class AccountTradeQueryBuider extends QueryBuilder {

  @ApiModelProperty("常量：TRADE_TYPE")
  private List<Integer> types = TradeType.defaults();

  @ApiModelProperty(hidden = true)
  private long employerId;

  private int startTime;

  private int endTime;

  public List<Integer> getTypes() {
    return types;
  }

  public void setTypes(List<Integer> types) {
    this.types = types;
  }

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public int getStartTime() {
    return startTime;
  }

  public void setStartTime(int startTime) {
    this.startTime = startTime;
  }

  public int getEndTime() {
    return endTime;
  }

  public void setEndTime(int endTime) {
    this.endTime = endTime;
  }
}
