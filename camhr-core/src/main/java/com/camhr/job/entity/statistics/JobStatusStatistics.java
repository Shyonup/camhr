package com.camhr.job.entity.statistics;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Allen on 2018/9/25.
 */
public class JobStatusStatistics {

  /**
   * 已上线的 职位 数量
   */
  @ApiModelProperty("已上线的 职位 数量")
  private int online;

  /**
   * 下线的 职位 数量
   */
  @ApiModelProperty("下线的 职位 数量")
  private int offline;

  /**
   * 未上线的 职位 数量
   */
  @ApiModelProperty("未上线的 职位 数量")
  private int draft;

  // ==================================== get / set ====================================

  public int getOnline() {
    return online;
  }

  public void setOnline(int online) {
    this.online = online;
  }

  public int getOffline() {
    return offline;
  }

  public void setOffline(int offline) {
    this.offline = offline;
  }

  public int getDraft() {
    return draft;
  }

  public void setDraft(int draft) {
    this.draft = draft;
  }
}
