package com.camhr.employer.entity;

import io.swagger.annotations.ApiModelProperty;

public class EmployerJobApplyStatistics {

  @ApiModelProperty("带筛选总数")
  private int chosenTotal;
  @ApiModelProperty("待沟通")
  private int comunicationTotal;
  @ApiModelProperty("已邀请")
  private int invitedTotal;
  @ApiModelProperty("不合适")
  private int incompatibleTotal;

  public int getChosenTotal() {
    return chosenTotal;
  }

  public void setChosenTotal(int chosenTotal) {
    this.chosenTotal = chosenTotal;
  }

  public int getComunicationTotal() {
    return comunicationTotal;
  }

  public void setComunicationTotal(int comunicationTotal) {
    this.comunicationTotal = comunicationTotal;
  }

  public int getInvitedTotal() {
    return invitedTotal;
  }

  public void setInvitedTotal(int invitedTotal) {
    this.invitedTotal = invitedTotal;
  }

  public int getIncompatibleTotal() {
    return incompatibleTotal;
  }

  public void setIncompatibleTotal(int incompatibleTotal) {
    this.incompatibleTotal = incompatibleTotal;
  }

}
