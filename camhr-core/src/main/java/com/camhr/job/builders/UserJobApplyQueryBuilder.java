package com.camhr.job.builders;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Allen on 2018/9/19.
 */
public class UserJobApplyQueryBuilder extends JobApplyQueryBuilder {

  @ApiModelProperty("面试人姓名")
  private String  intervieweeName;

  @ApiModelProperty("联系方式")
  private String mobilePhone;

  @ApiModelProperty("公司名称")
  private String company;

  // ==================================== get / set ====================================

  public String getIntervieweeName() {
    return intervieweeName;
  }

  public void setIntervieweeName(String intervieweeName) {
    this.intervieweeName = intervieweeName;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}
