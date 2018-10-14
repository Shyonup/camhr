package com.camhr.job.service.impl;

import com.camhr.constants.CamConstantKey;
import com.camhr.constants.ServiceItem;
import com.camhr.employer.constants.EmployerStatus;
import com.camhr.employer.entity.Employer;
import com.camhr.employer.product.constants.EmployerProductItemStatus;
import com.camhr.employer.product.entity.EmployerProductItem;
import com.camhr.employer.product.service.EmployerProductService;
import com.camhr.employer.product.service.ProductService;
import com.camhr.employer.service.EmployerService;
import com.camhr.job.builders.JobQueryBuilder;
import com.camhr.job.config.JobConfiguration;
import com.camhr.job.constants.JobStatus;
import com.camhr.job.constants.PublishType;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobLocation;
import com.camhr.job.entity.PublishJob;
import com.camhr.job.entity.TopJob;
import com.camhr.job.entity.statistics.JobApplyStatusStatistics;
import com.camhr.job.error.JobErrorCodes;
import com.camhr.job.mapper.JobMapper;
import com.camhr.job.service.JobApplyStatisticsService;
import com.camhr.job.service.JobService;
import com.camhr.job.utils.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Constant;
import we.lang.Constants;
import we.lang.Exceptions;
import we.util.Page;

/**
 * Created by Allen on 2018/9/10.
 */
@Service
public class JobServiceImpl implements JobService {

  private Set<Integer> locationIdsFromDB;

  @Autowired
  private JobMapper jobMapper;

  @Autowired
  private EmployerService employerService;

  @Autowired
  private ProductService productService;

  @Autowired
  private EmployerProductService employerProductService;  // 企业  发布职位服务包 相关

  @Autowired
  private JobApplyStatisticsService jobApplyStatisticsService;

  @Override
  public Page<Job> queryJobs(JobQueryBuilder jobQueryBuilder) {
    Page<Job> page = Page.of(jobQueryBuilder);
    List<Job> jobList = jobMapper.queryJobs(jobQueryBuilder);
    fetchLocations(jobList);
    page.setResult(jobList);
    page.setTotalCount(jobMapper.countJobs(jobQueryBuilder));
    return page;
  }

  @Override
  @Transactional
  public int addJob(Job job) {
    job.setCreatedate(TimeUtil.getNowDate());
    job.setUpdatetime(job.getCreatedate());

    int row = jobMapper.addJob(job);
    if (row > 0) {
      addJobLocations(job);
    }
    return row;
  }

  @Override
  @Transactional
  public int removeJob(long jobId, long employerId) {
    Integer jobStatusFromDB = jobMapper.getJobStatusByEmployerId(jobId, employerId);
    /* 数据校验 */
    if (jobStatusFromDB == null) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }

    /* Step_1 草稿的职位，如果客户点击删除，直接物理删除 */
    if (jobStatusFromDB == JobStatus.DRAFT.value()) {
      return deleteJob(jobId, employerId);
    }

    /* Step_2 如果已经发布，点击删除的话，先调用 职位下线 接口 */
    if (jobStatusFromDB == JobStatus.PUBLISHED.value()) {
      closeJob(Job.of(jobId, employerId));
    }

    /* Step_3 逻辑删除职位信息 */
    return jobMapper.removeJob(jobId, employerId);
  }

  @Override
  @Transactional
  public int updateJob(Job job) {
    /* 数据校验 */
    // 已删除就不允许再更新了
    Integer jobStatusFromDB = jobMapper.getJobStatusByEmployerId(job.getId(), job.getEmployerId());
    if (jobStatusFromDB == null || jobStatusFromDB == JobStatus.REMOVED.value()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }

    job.setUpdatetime(TimeUtil.getNowDate());
    int row = jobMapper.updateJob(job);
    if (row > 0) {
     jobMapper.deleteJobLocations(job.getId());
     addJobLocations(job);
     // jobMapper.deleteJobLanguage(job.getId()); // 现在前端没有传language数据过来
    }
    return row;
  }

  @Override
  public Job getJob(long jobId, long employerId) {
    Job job = getJob(jobId);
    if (job != null && job.getEmployerId() != employerId) {
      job = null; // 查别人公司的职位信息，不返回数据
    }

    if (job != null) {
      // 获取面试人数的统计数据
      fetchJobIntervieweeStatistics(Lists.newArrayList(job));
    }
    return job;
  }

  @Override
  public Job getJob(long jobId) {
    Job job = getJobBaseInfo(jobId);
    if (job != null) {
      fetchLocations(Lists.newArrayList(job)); // 获取location信息
    }
    return job;
  }

  @Override
  public int refreshStatisticOfApplyingJob(long jobId) {
    return jobMapper.refreshStatisticOfApplyingJob(jobId);
  }

  @Override
  public Job getJobBaseInfo(long jobId) {
    return jobMapper.getJob(jobId);
  }

  @Override
  @Transactional
  public int publishJob(PublishJob publishJob) {
    long jobId = publishJob.getJobId();
    long employerProductId = publishJob.getEmployerProductItemId();
    long employerId = publishJob.getEmployerId();

    Job jobFromDB = jobMapper.getJob(jobId);
    Employer employerFromDB = employerService.getEmployerBaseInfo(employerId);

    /* 数据校验 */
    if (jobFromDB == null) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }
    // 已删除就不能发布了
    if (jobFromDB.getStatus() == JobStatus.REMOVED.value()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }
    // 发布职位的企业，必须处于正常状态
    if (employerFromDB == null || employerFromDB.getStatus() != EmployerStatus.OK.value()) {
      Exceptions.of(JobService.class).error(JobErrorCodes.EMPLOYER_STATUS_IS_NOT_OK);
    }
    // 已发布就不要重复发布了
    if (jobIsOnline(jobFromDB)) { // 处于发布状态
      Exceptions.of(JobService.class).error(JobErrorCodes.JOB_HAD_PUBLISHED);
    }

    /* 业务逻辑 */
    // 初始化默认值
    boolean isUrgent = false;
    int publishType = PublishType.BASIC_JOB;
    int publishedDays = JobConfiguration.JOB_FREE_DISPLAY_DAYS; // 发布时长

    // 判断该企业发布职位是否免费
    boolean isPublishFree = employerService.isPublishJobFree(employerId);
    if (!isPublishFree || employerProductId > 0) { // 如果发布要收费的话 或者 发布时选了服务包
      // Step_1 校验 企业购买的服务包 数据
      EmployerProductItem productItemFromDB = employerProductService.getEmployerProductItem(employerProductId);
      validateBalance(employerId, productItemFromDB); // 校验服务包数据，不通过的抛异常

      // Step_2 根据服务包数据，获取 isUrgent、publishType、maxPublishedDays等数据
      int itemId = productItemFromDB.getItemId(); // itemId是B_TIEM表的数据。
      if (itemId == ServiceItem.URGENT_JOB || itemId == ServiceItem.FREE_URGENT_JOB) {
        isUrgent = true;
      } else if (itemId == ServiceItem.STANDARD_JOB) {
        publishType = PublishType.STANDARD_JOB;
      }
      publishedDays = productItemFromDB.getDisplay();

      // Step_3 先扣除服务包
      employerProductService.useProductItem(employerId, jobId, employerProductId, 1); /*
        1表示扣除一个服务包，代码写死，旧代码逻辑如此。
      */
    }

    jobFromDB.setStatus(JobStatus.PUBLISHED.value());
    jobFromDB.setIspublish(true);
    jobFromDB.setPubdate(checkPublishDate(jobFromDB.getPubdate()));
    jobFromDB.setExpdate(TimeUtil.getMidnightDate(publishedDays));
    jobFromDB.setCloseDate(jobFromDB.getExpdate()); // 默认职位默认下线时间，就是过期时间
    jobFromDB.setUpdatetime(TimeUtil.getNowDate());
    jobFromDB.setIsurgent(isUrgent);
    jobFromDB.setPubtype(publishType);

    return jobMapper.publishJob(jobFromDB);
  }

  /**
   * 校验 企业所购买服务的余额是否充足
   */
  private boolean validateBalance(long employerId, EmployerProductItem productItemFromDB) {
    if (productItemFromDB == null
        || productItemFromDB.getEmployerId() != employerId
        || productItemFromDB.getStatus() == EmployerProductItemStatus.REMOVED.value()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.EMPLOYER_PRODUCT_ITEM_NOT_FOUNT);
    }
    // 前端传过来的productId，必须是与发布职位相关的
    if (!ServiceItem.itemIdAboutJob().contains(productItemFromDB.getItemId())) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.PRODUCT_IS_NOT_ABOUT_PUBLISHED_JOB);
    }
    if (productItemFromDB.getAmount() <= 0) { // 余额不足
      Exceptions.of(JobService.class).notFound(JobErrorCodes.AMOUNT_OF_PRODUCT_ITEM_IS_NOT_ENOUGH);
    }

    return true;
  }

  /**
   * publishDate 表示职位什么什么时候发布的。
   *    这个发布时间可以是【此时此刻】，也可以使未来的某个时间点
   *    数据校验逻辑 是从旧代码搬过来的
   */
  private Date checkPublishDate(Date publishDate) {
    Date todayMidnight = TimeUtil.getMidnightDate(0); // 当天的0晨0点
    if (publishDate == null) {
      return todayMidnight; // 那就把发布时间改成今天的0晨0点
    }

    if (publishDate.before(todayMidnight)) { // 设好了预发布时间，但一直没有发布。现在都过了预发布时间了，才点击发布
      return todayMidnight; // 那就把发布时间改成今天的0晨0点
    }

    Date maxPublishDate = TimeUtil.getMidnightDate(JobConfiguration.JOB_PUBLISH_PRE_DAY);
    if (publishDate.after(maxPublishDate)) { // 预发布的时间，不能最大不能超过 30后再发布（配置：JOB_PUBLISH_PRE_DAY）
      return maxPublishDate; // 如果超过了，就强行设置成30天后发布。
    }
    return publishDate;
  }

  /**
   * 判断职位是否处于上线状态
   * @return
   */
  private boolean jobIsOnline(Job job) {
    boolean flag = false;
    if (job.getStatus() == JobStatus.PUBLISHED.value()) { // 处于发布状态
      if (!jobIsClosedOrExpired(job)) { // 未关闭
        flag = true;
      }
    }
    return flag;
  }

  private boolean jobIsClosedOrExpired(Job job) {
    if (job.getExpdate() == null || job.getCloseDate() == null) {
      return false;
    }

    boolean flag = false;
    if (job.getExpdate().before(TimeUtil.getNowDate()) || // 已过期
        job.getCloseDate().before(TimeUtil.getNowDate())) { // 已关闭
      flag = true;
    }
    return flag;
  }

  @Override
  @Transactional
  public int closeJob(Job job) {
    /* 数据校验 */
    Integer jobStatusFromDB = jobMapper.getJobStatusByEmployerId(job.getId(), job.getEmployerId());
    // 数据库查不出数据
    if (jobStatusFromDB == null || jobStatusFromDB == JobStatus.REMOVED.value()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }
    // 只能关闭已发布状态的职位
    if (jobStatusFromDB != JobStatus.PUBLISHED.value()) {
      Exceptions.of(JobService.class).error(JobErrorCodes.CLOSE_A_UNPUBLISHED_JOB);
    }

    job.setCloseDate(TimeUtil.getNowDate());
    int row = jobMapper.closeJob(job);
    if (row > 0) {
      // 如果是手动点击职位下线，就同时把 自动续费功能 给关闭掉
      updateJobAutoRenewStatus(job.getId(), job.getEmployerId(), false);
    }
    return row;
  }

  /**
   * 获取 职位的 面试人数统计数据
   */
  @Override
  public void fetchJobIntervieweeStatistics(List<Job> jobList) {
    if (jobList == null || jobList.isEmpty()) {
      return;
    }

    // 从统计的service获取统计数据
    List<JobApplyStatusStatistics> intervieweeStatisticsList = jobApplyStatisticsService
        .getJobApplyStatusStatisticsByJobIds(getJobIds(jobList));
    Map<Long, JobApplyStatusStatistics> intervieweeStatisticsMap = toMap(
        intervieweeStatisticsList);

    // 把统计数据塞到JobList里面
    for (Job job : jobList) {
      JobApplyStatusStatistics intervieweeStatistics = intervieweeStatisticsMap.get(job.getId());
      if (intervieweeStatistics != null) {
        job.setIntervieweeStatistics(intervieweeStatistics);
      }
    }
  }

  @Override
  public Page<Job> queryEmployerJobs(JobQueryBuilder jobQueryBuilder) {
    Page<Job> jobPage = queryJobs(jobQueryBuilder);
    fetchJobIntervieweeStatistics(jobPage.getResult()); // 获取面试人数的统计数据
    return jobPage;
  }

  @Override
  public int updateJobAutoRenewStatus(long jobId, long employerId, boolean isAutoRenew) {
    int row = jobMapper.updateJobAutoRenewStatus(jobId, employerId, isAutoRenew);
    if (row > 0) {
      Job jobFromDB = getJob(jobId);
      //  如果职位曾经发不过，并且处于 已经关闭 或 过期状态，就再发布一次
      if (isAutoRenew && jobFromDB.isIspublish() && jobIsClosedOrExpired(jobFromDB)) {
        Long employerProductItemId = jobMapper.getEmployerProductItemIdWhenLastPublishJob(jobId);
        publishJob(new PublishJob(
            jobId,
            employerId,
            employerProductItemId == null ? 0 : employerProductItemId
        ));
      }
    }
    return row;
  }

  @Override
  public List<PublishJob> findJobsWhichNeedToAutoRenew() {
    JobQueryBuilder jobQueryBuilder = new JobQueryBuilder();
    jobQueryBuilder.setOffline(true); // 需要自动续费的，必须是已下线的
    return jobMapper.findJobsWhichNeedToAutoRenew(jobQueryBuilder);
  }

  @Override
  public int closeExpiredJobs() {
    return jobMapper.closeExpiredJobs(TimeUtil.getNowDate());
  }

  @Override
  @Transactional
  public int topJob(TopJob topJob) {
    /* 数据校验 */
    long employerId = topJob.getEmployerId();
    long jobId = topJob.getJobId();
    long employerProductItemId = topJob.getEmployerProductItemId();
    int quantity = topJob.getQuantity();

    Job jobFromDB = jobMapper.getJob(jobId);
    // 外键校验
    if (jobFromDB == null) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }
    boolean jobIsOnline = jobIsOnline(jobFromDB);
    // 置顶服务职能用在已发布的职位
    if (!jobIsOnline) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.TOB_JOB_AND_JOB_IS_UNPUBLISHED);
    }
    // 紧急职位，无法使用置顶服务
    if (jobIsOnline && jobFromDB.isIsurgent()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.TOB_JOB_AND_JOB_IS_URGENT);
    }
    // 已经置顶了，无需再次置顶
    if (jobIsOnline && jobFromDB.isTop() && jobFromDB.getTopServiceExpDate().after(TimeUtil.getNowDate())) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.JOB_IS_TOP);
    }

    EmployerProductItem employerProductItemFromDB = employerProductService
        .getEmployerProductItem(employerProductItemId);
    // 外键校验
    if (employerProductItemFromDB == null
        || employerProductItemFromDB.getEmployerId() != employerId
        || employerProductItemFromDB.getStatus() == EmployerProductItemStatus.REMOVED.value()) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.EMPLOYER_PRODUCT_ITEM_NOT_FOUNT);
    }
    // 服务类型校验
    if (employerProductItemFromDB.getItemId() != ServiceItem.TOP_JOB) {
      Exceptions.of(JobService.class).notFound(JobErrorCodes.PRODUCT_IS_NOT_ABOUT_TOP_JOB);
    }

    /* 业务逻辑 */
    boolean useProductItemSuccessfully = employerProductService.useProductItem(
        employerId, jobId, employerProductItemId, 1) > 0; // 使用数量默认是1，以后可能是quantity，前端传过来的
    if (useProductItemSuccessfully) {
      Date jobServiceExpiredDate = jobFromDB.getExpdate(); // 一共置顶多少天，现在默认是和职位过期同步
      jobMapper.topJob(jobId, employerId, jobServiceExpiredDate);
    }

    return 1;
  }

  @Override
  public int downTheTopJobsWhichIsExpired() {
    return jobMapper.downTheTopJobsWhichIsExpired(TimeUtil.getNowDate());
  }

  /**
   * Job List集合转Map集合, map中的key是job对象的id
   */
  private Map<Long, JobApplyStatusStatistics> toMap(
      List<JobApplyStatusStatistics> intervieweeStatistics) {
    Map<Long, JobApplyStatusStatistics> map = Maps.newHashMap();
    for (JobApplyStatusStatistics intervieweeStatistic : intervieweeStatistics) {
      map.put(intervieweeStatistic.getJobId(), intervieweeStatistic);
    }
    return map;
  }

  private Set<Long> getJobIds(List<Job> jobList) {
    Set<Long> jobIds = Sets.newHashSet();
    for (Job job : jobList) {
      jobIds.add(job.getId());
    }
    return jobIds;
  }

  private int deleteJob(long jobId, long employerId) {
    int row = jobMapper.deleteJob(jobId, employerId);
    if (row > 0) {
      jobMapper.deleteJobLocations(jobId);
      jobMapper.deleteJobLanguage(jobId);
    }
    return row;
  }

  private void addJobLocations(Job job) {
    List<JobLocation> locations = filterLocations(job.getLocations());
    if (!locations.isEmpty()) {
      for (JobLocation jobLocation : locations) {
        jobLocation.setJobId(job.getId());
      }

      jobMapper.addJobLocations(locations);
    }
  }

  /**
   * 过滤掉Location（程序）相关的垃圾数据
   * 因为LocationId作为外键被其他数据库表引用，所以这里要过滤
   */
  private List<JobLocation> filterLocations(List<JobLocation> locations) {
    // 初始化常量数据到内存中
    if (locationIdsFromDB == null) { // 单例模式
      locationIdsFromDB = Sets.newHashSet();
      for (Constant locationConstant : Constants.of(CamConstantKey.LOCATION).values()) {
        locationIdsFromDB.add((Integer) locationConstant.getValue());
      }
    }

    // 非空判断
    if (locations == null || locations.isEmpty()) {
      return Lists.newArrayList();
    }

    // 过滤数据操作
    List<JobLocation> availableLocations = Lists.newArrayList();
    for (JobLocation location : locations) {
      if (locationIdsFromDB.contains(location.getLocationId())) {
        availableLocations.add(location);
      }
    }
    return availableLocations;
  }

  /**
   * 给Job里面的Locations属性赋值
   */
  private List<Job> fetchLocations(List<Job> jobs) {
    if (jobs == null || jobs.isEmpty()) {
      return jobs;
    }

    // 准备查询条件数据
    Set<Long> jobIds = Sets.newHashSet(); // 用于查询JobLocations
    Map<Long, Job> jobMap = Maps.newHashMap(); // 把Job主键抽出来作为Map集合Key，方便快速检索
    for (Job job : jobs) {
      jobIds.add(job.getId());
      job.setLocations(Lists.newArrayList());
      jobMap.put(job.getId(), job);
    }

    // 把从数据库查出来的jobLocations数据，按jobId的塞到job里面
    List<JobLocation> jobLocations = jobMapper.findJobLocations(jobIds);
    for (JobLocation jobLocation : jobLocations) {
      jobMap.get(jobLocation.getJobId()) // 从Map集合中把Job对象取出来
          .getLocations() // 从Job对象中把Locations集合取出来
          .add(jobLocation); // 把数据库查出来的Location数据塞进去
    }

    return jobs;
  }

}