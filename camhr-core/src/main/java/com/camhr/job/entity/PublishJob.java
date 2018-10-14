package com.camhr.job.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;

/**
 * 发布职位时，前端传输数据到后台用到的实体类
 *
 * Created by Allen on 2018/10/8.
 */
public class PublishJob {

  @ApiModelProperty(readOnly = true)
  private long jobId; // 从URL中获取

  @JsonProperty(access = Access.READ_ONLY)
  private long employerId; // 从登陆信息中获取

  @ApiModelProperty("外键，企业购买的服务包ID")
  private long employerProductItemId; // E_SERVICE_ITEM 表的主键，表示企业 已购买的服务

  // ==================================== 构造方法 ====================================

  public PublishJob() {
  }

  public PublishJob(long jobId, long employerId, long employerProductItemId) {
    this.jobId = jobId;
    this.employerId = employerId;
    this.employerProductItemId = employerProductItemId;
  }

  // ==================================== get / set ====================================

  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  @ApiModelProperty(readOnly = true)
  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public long getEmployerProductItemId() {
    return employerProductItemId;
  }

  public void setEmployerProductItemId(long employerProductItemId) {
    this.employerProductItemId = employerProductItemId;
  }
}
