package com.camhr.job.builders;

import com.camhr.job.utils.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class EmployerJobApplyQueryBuilder extends JobApplyQueryBuilder {

  @ApiModelProperty("期望工作地点")
  private int locationId;

  @ApiModelProperty("最小年龄")
  private Integer fromAge;

  @ApiModelProperty("最大年龄")
  private Integer toAge;

  @ApiModelProperty("工作名称")
  private String jobTitle;

  @ApiModelProperty("职位id")
  private Long jobId;

  @ApiModelProperty(hidden = true)
  private Date fromBirthday; // 数据库SQL查询用到

  @ApiModelProperty(hidden = true)
  private Date toBirthday; // 数据库SQL查询用到

  // ==================================== get / set ====================================

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public Integer getFromAge() {
    return fromAge;
  }

  public void setFromAge(Integer fromAge) {
    this.fromAge = fromAge;
    if (fromAge != null) {
      this.toBirthday = TimeUtil.countBirthday(fromAge);
    }
  }

  public Integer getToAge() {
    return toAge;
  }

  public void setToAge(Integer toAge) {
    this.toAge = toAge;
    if (toAge != null) {
      this.fromBirthday = TimeUtil.countBirthday(toAge);
    }
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle.toLowerCase();
  }

  public Date getFromBirthday() {
    return fromBirthday;
  }

  public void setFromBirthday(Date fromBirthday) {
    this.fromBirthday = fromBirthday;
  }

  public Date getToBirthday() {
    return toBirthday;
  }

  public void setToBirthday(Date toBirthday) {
    this.toBirthday = toBirthday;
  }

  public Long getJobId() {
    return jobId;
  }

  public void setJobId(Long jobId) {
    this.jobId = jobId;
  }
}
