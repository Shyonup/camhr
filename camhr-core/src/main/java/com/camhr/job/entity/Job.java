package com.camhr.job.entity;

import com.camhr.job.constants.JobLanguage;
import com.camhr.job.constants.JobStatus;
import com.camhr.job.entity.statistics.JobApplyStatusStatistics;
import com.camhr.job.utils.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import we.business.annotation.ForeignProperty;
import we.json.AsConstant;

/**
 * 职位
 *
 * Note: 有些属性名没遵循Java的命名规范，有些属性名单词打错了。请无视，因为数据库表是这样子
 */
@ApiModel
public class Job {

  // =========================== 数据库字段 ===========================
  @JsonProperty(access = Access.READ_ONLY)
  private long id;

  @AsConstant("JOB_STATUS")
  private int status = JobStatus.DRAFT.value(); // 状态：0-草稿，1-发布，2.关闭，9-删除

  @JsonProperty(access = Access.READ_ONLY)
  private boolean ispublish; // 0-未发布，1-已发布

  /**
   * 发布时间：后台自动生成：前端什么时候点击上线，获取当天时间的00:00（0晨0点）
   */
  @JsonProperty(access = Access.READ_ONLY)
  private Date pubdate;

  /**
   * 失效时间：后台自动生成 pubdate + 前端自定义的发布天数
   */
  @JsonProperty(access = Access.READ_ONLY)
  private Date expdate;

  @Length(max = 255)
  @NotBlank
  private String title; // 职位名称，前端必传，length 512

  private Integer hirelings; // 雇佣的人数

  private int workyears; // 最低工作年限要求

  @JsonProperty(access = Access.READ_ONLY)
  private String othersQualification; // 如果列表内无合适的学历要求，雇主可以在这里填写自定义的学历要求 ,length 150

  private Integer ageFrom;

  private Integer ageTo;

  @JsonIgnore // 为了支持多地区，这个address不用了，采用下面的locations
  private String address; // length 512

  @Length(max = 256)
  private String weburl; // length 256

  @Length(max = 4000)
  private String requirement; // 基本要求, length 4000

  private String description; // 工作描述, oracle：Long类型

  private boolean isurgent; // 紧急工作,0-不是，1-是

  @JsonIgnore
  private boolean isTop; // 职位是否置顶，后台排序用的，不需要返回给前端

  @JsonProperty(access = Access.READ_ONLY)
  private Date topServiceExpDate; // 置顶服务过期时间，后台生成。

  @JsonProperty(access = Access.READ_ONLY)
  private Date updatetime; // 更新时间, 数据库默认给值

  private Date createdate; // 数据库默认给值

  @JsonProperty(access = Access.READ_ONLY)
  private int newApply = 0; // 新申请

  @JsonProperty(access = Access.READ_ONLY)
  private int totalApply = 0; // 总申请

  @JsonProperty(access = Access.READ_ONLY)
  private int searchApply = 0; // 企业查找总数

  @JsonProperty(access = Access.READ_ONLY)
  private Long seekerHits = 0L; // 用户访问数

  private Date sysclose; // 系统关闭时间

  private Date hotExpdate; // 热门结束时间

  /**
   * 职位下线时间 后台自动生成，如果 HR 不主动下线，下线时间 默认 就是 expdate
   */
  @JsonProperty(access = Access.READ_ONLY)
  private Date closeDate; // 企业关闭时间

  @Length(max = 256)
  private String onlineApply; // 企业可以填写自己网站职位信息的链接地址 length 256

  @Length(max = 8)
  private String showEmail; // 选择是否向求职者展示邮箱 length 8

  @JsonProperty(access = Access.READ_ONLY)
  private int publishedDays; // 一共上线多少天

  // ---------- 外键 ----------

  @ForeignProperty(name = "employer", value = "@employerService.getEmployerBaseInfo(employerId)")
  private long employerId; // 从登陆信息中读取，Not Null

  @ForeignProperty(name = "contact", value = "@contactService.getContactBaseInfo(contactId)")
  private Long contactId;

  @AsConstant("INDUSTRIAL")
  private Integer industrialId;

  @AsConstant("JOB_CATEGORY")
  private Integer categoryId;

  @AsConstant("JOBTERM")
  private Integer termId; // 工作性质

  @AsConstant("JOB_LEVEL")
  private Integer jobLevelId;

  @AsConstant("SALARY")
  private Integer salaryId; // 薪酬

  @AsConstant("QUALIFICATION")
  private int qualificationId; // 学历要求

  @AsConstant("SEX")
  private Integer sex;

  @AsConstant("MARITAL")
  @JsonProperty("marital") // 数据库表的字段打错了
  private Integer matrial; // 已婚未婚、从数据库表读出来

  @AsConstant("PUBLISH_TYPE")
  private Integer pubtype = 1; // 外键，从数据库查询，1-basic，2-standard; 旧的代码就是写死一个值的。前端用传过来

  @JsonIgnore // 采用List集合
  private String major; // length 355，专业，常量

  private String language = JobLanguage.EN.value(); // 语言

  @ApiModelProperty("是否到期后 自动续费")
  private boolean autoRenew;

  // =========================== 冗余字段，方便前端传输数据 ===========================

  @ApiModelProperty(readOnly = true, value = "剩余多少天过期")
  private int expiredDays;

  /**
   * 地址，查询时会返回给前端
   */
  @Size(min = 1)
  @NotNull
  private List<JobLocation> locations = Lists.newArrayList();

  /**
   * 面试人数统计数据
   */
  private JobApplyStatusStatistics intervieweeStatistics = new JobApplyStatusStatistics();

  // ==================================== 普通方法 ====================================
  public static Job of(long employerId) {
    Job job = new Job();
    job.setEmployerId(employerId);
    return job;
  }

  public static Job of(long jobId, long employerId) {
    Job job = new Job();
    job.setId(jobId);
    job.setEmployerId(employerId);
    return job;
  }

  // ==================================== get / set ====================================
  @ApiModelProperty(readOnly = true) // 自增长主键，不需要前端传过来
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @ApiModelProperty("状态：0-草稿，1-发布，2.关闭，9-删除")
  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @ApiModelProperty(readOnly = true, value = "0-未发布，1-已发布, 后台自动设置")
  public boolean isIspublish() {
    return ispublish;
  }

  public void setIspublish(boolean ispublish) {
    this.ispublish = ispublish;
  }

  @ApiModelProperty(readOnly = true, value = "发布时间，后台生成")
  public Date getPubdate() {
    return pubdate;
  }

  public void setPubdate(Date pubdate) {
    this.pubdate = pubdate;
  }

  @ApiModelProperty(value = "失效时间，后台自行计算（前端用不上）", readOnly = true)
  public Date getExpdate() {
    return expdate;
  }

  public void setExpdate(Date expdate) {
    this.expdate = expdate;
    if (expdate != null) {
      int expiredDays = TimeUtil.countExpiredDays(expdate);
      this.expiredDays = expiredDays < 0 ? 0 : expiredDays; // 如果为负数，就改成0
    }
  }

  @ApiModelProperty(readOnly = true, value = "雇主Id，外键")
  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  @ApiModelProperty(readOnly = true, value = "联系人Id，外键")
  public Long getContactId() {
    return contactId;
  }

  public void setContactId(Long contactId) {
    this.contactId = contactId;
  }

  @ApiModelProperty(value = "职位标题，必传", example = "招募令")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @ApiModelProperty(value = "雇用人数", example = "5")
  public Integer getHirelings() {
    return hirelings;
  }

  public void setHirelings(Integer hirelings) {
    this.hirelings = hirelings;
  }

  @ApiModelProperty(value = "行业Id，外键", example = "113", readOnly = true)
  public Integer getIndustrialId() {
    return industrialId;
  }

  public void setIndustrialId(Integer industrialId) {
    this.industrialId = industrialId;
  }

  @ApiModelProperty(value = "职位分类Id，常量: CATEGORY", example = "1")
  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  @ApiModelProperty(value = "工作性质：全职、兼职等，常量：JOBTERM", example = "1")
  public Integer getTermId() {
    return termId;
  }

  public void setTermId(Integer termId) {
    this.termId = termId;
  }

  @ApiModelProperty(hidden = true, value = "后台排序用的")
  public Integer getJobLevelId() {
    return jobLevelId;
  }

  public void setJobLevelId(Integer jobLevelId) {
    this.jobLevelId = jobLevelId;
  }

  @ApiModelProperty(value = "薪资范围，常量：SALARY", example = "1")
  public Integer getSalaryId() {
    return salaryId;
  }

  public void setSalaryId(Integer salaryId) {
    this.salaryId = salaryId;
  }

  @ApiModelProperty("最低工作年限要求")
  public int getWorkyears() {
    return workyears;
  }

  public void setWorkyears(int workyears) {
    this.workyears = workyears;
  }

  @ApiModelProperty(value = "学历，常量：QUALIFICATION", example = "1")
  public int getQualificationId() {
    return qualificationId;
  }

  public void setQualificationId(int qualificationId) {
    this.qualificationId = qualificationId;
  }

  @ApiModelProperty(value = "特殊的学历要求，字符串，随意填", example = "特殊的学历要求", readOnly = true)
  public String getOthersQualification() {
    return othersQualification;
  }

  public void setOthersQualification(String othersQualification) {
    this.othersQualification = othersQualification;
  }

  @ApiModelProperty(hidden = true)
  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  @ApiModelProperty(value = "性别，常量：SEX", example = "3", readOnly = true)
  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  @ApiModelProperty(value = "是否已婚，常量：MARITAL", example = "3", readOnly = true)
  public Integer getMatrial() {
    return matrial;
  }

  public void setMatrial(Integer matrial) {
    this.matrial = matrial;
  }

  @ApiModelProperty(value = "工作年龄，最小", example = "18", readOnly = true)
  public Integer getAgeFrom() {
    return ageFrom;
  }

  public void setAgeFrom(Integer ageFrom) {
    this.ageFrom = ageFrom;
  }

  @ApiModelProperty(value = "工作年龄，最大", example = "45", readOnly = true)
  public Integer getAgeTo() {
    return ageTo;
  }

  public void setAgeTo(Integer ageTo) {
    this.ageTo = ageTo;
  }

  @ApiModelProperty(hidden = true)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @ApiModelProperty(readOnly = true, value = "公司网址")
  public String getWeburl() {
    return weburl;
  }

  public void setWeburl(String weburl) {
    this.weburl = weburl;
  }

  @ApiModelProperty(value = "基本要求，长度4000", example = "工作职责：", readOnly = true)
  public String getRequirement() {
    return requirement;
  }

  public void setRequirement(String requirement) {
    this.requirement = requirement;
  }

  @ApiModelProperty(value = "工作详细描述，无长度要求", example = "工作详情描述")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @ApiModelProperty(value = "工作是否紧急", example = "false", readOnly = true)
  public boolean isIsurgent() {
    return isurgent;
  }

  public void setIsurgent(boolean isurgent) {
    this.isurgent = isurgent;
  }

  @ApiModelProperty(readOnly = true)
  public Date getUpdatetime() {
    return updatetime;
  }

  public void setUpdatetime(Date updatetime) {
    this.updatetime = updatetime;
  }

  @ApiModelProperty(readOnly = true)
  public Date getCreatedate() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

  @ApiModelProperty(readOnly = true, value = "新的申请人数")
  public int getNewApply() {
    return newApply;
  }

  public void setNewApply(int newApply) {
    this.newApply = newApply;
  }

  @ApiModelProperty(readOnly = true, value = "总申请人数")
  public int getTotalApply() {
    return totalApply;
  }

  public void setTotalApply(int totalApply) {
    this.totalApply = totalApply;
  }

  @ApiModelProperty(readOnly = true, value = "企业查找总数")
  public int getSearchApply() {
    return searchApply;
  }

  public void setSearchApply(int searchApply) {
    this.searchApply = searchApply;
  }

  @ApiModelProperty(readOnly = true, value = "用户访问数")
  public Long getSeekerHits() {
    return seekerHits;
  }

  public void setSeekerHits(Long seekerHits) {
    this.seekerHits = seekerHits;
  }

  @ApiModelProperty(value = "语言 前端传过来：常量: JOB_LANGUAGE", example = "en")
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language.toLowerCase();
  }

  @ApiModelProperty(value = "系统关闭时间", readOnly = true)
  public Date getSysclose() {
    return sysclose;
  }

  public void setSysclose(Date sysclose) {
    this.sysclose = sysclose;
  }

  @ApiModelProperty(value = "热门结束时间", readOnly = true)
  public Date getHotExpdate() {
    return hotExpdate;
  }

  public void setHotExpdate(Date hotExpdate) {
    this.hotExpdate = hotExpdate;
  }

  @ApiModelProperty(value = "企业关闭时间", readOnly = true)
  public Date getCloseDate() {
    return closeDate;
  }

  public void setCloseDate(Date closeDate) {
    this.closeDate = closeDate;
  }

  @ApiModelProperty(value = "常量：PUBLISH_TYPE", example = "1", readOnly = true)
  public Integer getPubtype() {
    return pubtype;
  }

  public void setPubtype(Integer pubtype) {
    this.pubtype = pubtype;
  }

  @ApiModelProperty(value = "企业可以填写自己网站职位信息的链接地址。长度：256", readOnly = true)
  public String getOnlineApply() {
    return onlineApply;
  }

  public void setOnlineApply(String onlineApply) {
    this.onlineApply = onlineApply;
  }

  @ApiModelProperty(value = "选择是否向求职者展示邮箱。长度：8", readOnly = true)
  public String getShowEmail() {
    return showEmail;
  }

  public void setShowEmail(String showEmail) {
    this.showEmail = showEmail;
  }

  @ApiModelProperty(value = "工作地址，locationId要常量（LOCATION）中获取")
  public List<JobLocation> getLocations() {
    return locations;
  }

  public void setLocations(List<JobLocation> locations) {
    this.locations = locations;
  }

  @ApiModelProperty(value = "一共上线多少天", example = "30")
  public int getPublishedDays() {
    return publishedDays;
  }

  public void setPublishedDays(int publishedDays) {
    this.publishedDays = publishedDays;
  }

  @ApiModelProperty(value = "面试人数统计数据", readOnly = true)
  public JobApplyStatusStatistics getIntervieweeStatistics() {
    return intervieweeStatistics;
  }

  public void setIntervieweeStatistics(
      JobApplyStatusStatistics intervieweeStatistics) {
    this.intervieweeStatistics = intervieweeStatistics;
  }

  public int getExpiredDays() {
    return expiredDays;
  }

  public void setExpiredDays(int expiredDays) {
    this.expiredDays = expiredDays;
  }

  public boolean isAutoRenew() {
    return autoRenew;
  }

  public void setAutoRenew(boolean autoRenew) {
    this.autoRenew = autoRenew;
  }

  @ApiModelProperty(hidden = true, value = "后台排序用的，职位是否置顶")
  public boolean isTop() {
    return isTop;
  }

  public void setTop(boolean top) {
    isTop = top;
  }

  @ApiModelProperty(readOnly = true, value = "置顶服务到期时间")
  public Date getTopServiceExpDate() {
    return topServiceExpDate;
  }

  public void setTopServiceExpDate(Date topServiceExpDate) {
    this.topServiceExpDate = topServiceExpDate;
  }
}
