package com.camhr.job.builders;

import com.camhr.job.constants.JobStatus;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import java.util.Set;
import we.query.QueryBuilder;

/**
 * Created by Allen on 2018/9/10.
 */
public class JobQueryBuilder extends QueryBuilder {

  @ApiModelProperty("常量：JOB_STATUS")
  private List<Integer> status = JobStatus.defaults();

  @ApiModelProperty("公司Id")
  private long employerId = -1L;

  @ApiModelProperty(value = "求职者Id", hidden = true)
  private long seekerId = -1L; // 后台从登陆信息中获取

  @ApiModelProperty("外键，城市Id，常量：LOCATION")
  private int locationId; // 城市

  @ApiModelProperty("职位标题或公司名称")
  private String jobTitleOrCompany; // 职位名称或公司名称

  @ApiModelProperty("职位标题")
  private String jobTitle; //

  @ApiModelProperty("公司名称")
  private String company;

  @ApiModelProperty(value = "语言, 常量：JOB_LANGUAGE", hidden = true)
  private String language;

  @ApiModelProperty(hidden = true)
  private Boolean closed; // 是否已经关闭

  @ApiModelProperty(hidden = true)
  private Boolean expired; // 是否已经过期

  @ApiModelProperty(hidden = true)
  private Boolean closedOrExpired; // 已关闭 或 过期

  @ApiModelProperty(hidden = true)
  private Boolean published; // 是否已经发布

  @ApiModelProperty("是否紧急")
  private Boolean urgent;

  @ApiModelProperty("是否上线")
  private boolean online;

  @ApiModelProperty("是否下线")
  private boolean offline;

  @ApiModelProperty("是否已经沟通过了")
  private Boolean hasCommunicated;

  @ApiModelProperty("学历，常量：QUALIFICATION")
  private Set<Integer> qualificationIds = Sets.newHashSet();

  @ApiModelProperty("薪资范围，常量：SALARY")
  private int salaryId = -1;

  @ApiModelProperty("行业，常量：INDUSTRIAL")
  private Set<Integer> industrialIds = Sets.newHashSet();

  @ApiModelProperty("岗位，常量：CATEGORY")
  private Set<Integer> categoryIds = Sets.newHashSet();

  @ApiModelProperty("公司规模（多少人），常量：EMPLOYER_SCALE")
  private Set<Integer> employerScaleIds = Sets.newHashSet();

  @ApiModelProperty("工作年限，最小")
  private int workYearMin = -1;

  @ApiModelProperty("工作年限，最大")
  private int workYearMax = -1;

  // ==================================== get / set ====================================

  public List<Integer> getStatus() {
    return status;
  }

  public void setStatus(List<Integer> status) {
    this.status = status;
  }

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public String getJobTitleOrCompany() {
    return jobTitleOrCompany;
  }

  public void setJobTitleOrCompany(String jobTitleOrCompany) {
    this.jobTitleOrCompany = jobTitleOrCompany.toLowerCase();
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle.toLowerCase();
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company.toLowerCase();
  }

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public long getSeekerId() {
    return seekerId;
  }

  public void setSeekerId(long seekerId) {
    this.seekerId = seekerId;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Boolean getClosed() {
    return closed;
  }

  public void setClosed(Boolean closed) {
    this.closed = closed;
  }

  public Date getNow() {
    return new Date();
  }

  public Boolean getPublished() {
    return published;
  }

  public void setPublished(Boolean published) {
    this.published = published;
  }

  public Boolean getClosedOrExpired() {
    return closedOrExpired;
  }

  public void setClosedOrExpired(Boolean closedOrExpired) {
    this.closedOrExpired = closedOrExpired;
  }

  public Boolean getExpired() {
    return expired;
  }

  public void setExpired(Boolean expired) {
    this.expired = expired;
  }

  public Boolean getUrgent() {
    return urgent;
  }

  public void setUrgent(Boolean urgent) {
    this.urgent = urgent;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
    if (online) { // 已经发布
      this.status = Lists.newArrayList(JobStatus.PUBLISHED.value()); // 职位处于发布状态
      this.closed = false; // 没有关闭
      this.expired = false; // 没有过期
    }
  }

  public boolean isOffline() {
    return offline;
  }

  public void setOffline(boolean offline) {
    this.offline = offline;
    if (offline) { // 下线
      this.published = true; // 职位曾经发布过
      this.closedOrExpired = true; // 现在过期了 或 被关闭了
    }
  }

  public Boolean getHasCommunicated() {
    return hasCommunicated;
  }

  public void setHasCommunicated(Boolean hasCommunicated) {
    this.hasCommunicated = hasCommunicated;
  }

  public Set<Integer> getQualificationIds() {
    return qualificationIds;
  }

  public void setQualificationIds(Set<Integer> qualificationIds) {
    this.qualificationIds = qualificationIds;
  }

  public int getSalaryId() {
    return salaryId;
  }

  public void setSalaryId(int salaryId) {
    this.salaryId = salaryId;
  }

  public Set<Integer> getIndustrialIds() {
    return industrialIds;
  }

  public void setIndustrialIds(Set<Integer> industrialIds) {
    this.industrialIds = industrialIds;
  }

  public Set<Integer> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(Set<Integer> categoryIds) {
    this.categoryIds = categoryIds;
  }

  public Set<Integer> getEmployerScaleIds() {
    return employerScaleIds;
  }

  public void setEmployerScaleIds(Set<Integer> employerScaleIds) {
    this.employerScaleIds = employerScaleIds;
  }

  public int getWorkYearMin() {
    return workYearMin;
  }

  public void setWorkYearMin(int workYearMin) {
    this.workYearMin = workYearMin;
  }

  public int getWorkYearMax() {
    return workYearMax;
  }

  public void setWorkYearMax(int workYearMax) {
    this.workYearMax = workYearMax;
  }
}
