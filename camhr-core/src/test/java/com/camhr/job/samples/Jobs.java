package com.camhr.job.samples;

import com.camhr.job.constants.JobStatus;
import com.camhr.job.constants.PublishType;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobLocation;
import com.camhr.job.utils.TimeUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.assertj.core.util.Lists;
import we.lang.Q;

/**
 * Created by Allen on 2018/9/13.
 */
public class Jobs {

  public static long employId = 9999999999L;

  /**
   * 发布 职位信息，需要修改一些字段
   */
  public static void changeJobInfoToPublish(Job job) {
    job.setStatus(JobStatus.PUBLISHED.value());
    job.setIspublish(true);
    job.setPubdate(TimeUtil.getMidnightDate(0)); // 获取当天时间的0晨0点
    job.setExpdate(TimeUtil.getMidnightDate(job.getPublishedDays())); // TODO 这里不允许超过最大值
    job.setCloseDate(job.getExpdate()); // 默认职位默认下线时间，就是过期时间
    job.setPubtype(PublishType.BASIC_JOB);
    job.setUpdatetime(TimeUtil.getNowDate());
  }


  /**
   * 下线状态的职位
   */
  public static Job createOfflineJob(long employerId) {
    Job job = createUnPublishJob("单元测试_下线_职位");
    job.setEmployerId(employerId);
    job.setPublishedDays(30);
    changeJobInfoToPublish(job);

    // 下线
    job.setExpdate(TimeUtil.getMidnightDate(-10)); // 十天前就过期了
    job.setCloseDate(TimeUtil.getMidnightDate(-10)); // 十天前就关闭了

    return job;
  }

  /**
   * 上线状态的职位
   */
  public static Job createOnlineJob(long employerId) {
    Job job = createUnPublishJob("单元测试_已上线_职位");
    job.setEmployerId(employerId);
    job.setPublishedDays(30);
    changeJobInfoToPublish(job);
    return job;
  }

  /**
   * 草稿状态的职位
   */
  public static Job createDraftJob(long employerId) {
    Job unPublishJob = createUnPublishJob("单元测试_未发布职位");
    unPublishJob.setEmployerId(employerId);
    return unPublishJob;
  }

  /**
   * 草稿状态的职位
   */
  public static Job createUnPublishJob(String title) {
    Job job = createJob(title);
    job.setStatus(JobStatus.DRAFT.value());
    job.setIspublish(false);
    job.setPubdate(null); // 获取当天时间的0晨0点
    job.setExpdate(null); // TODO 这里不允许超过最大值
    job.setCloseDate(null); // 默认职位默认下线时间，就是过期时间
    job.setPubtype(0);
    job.setUpdatetime(null);
    return job;
  }

  public static Job createJob(String title) {
    Job job = new Job();
    job.setEmployerId(Q.now());
    job.setTitle(title);
    job.setRequirement("工作要求：英语必须好");
    job.setAgeFrom(18);
    job.setAgeTo(45);
    job.setExpdate(createExpireDate(30)); // 30天后过期
    job.setSex(3);
    job.setTermId(1);
    job.setSalaryId(1);
    job.setQualificationId(1);
    job.setPubtype(1);
    job.setMatrial(3);
    job.setWorkyears(1);
    job.setLanguage("en");
    job.setIndustrialId(4);
    job.getLocations().add(JobLocation.of(1, 0));
    job.setHirelings(6);
    job.setCategoryId(1);
    job.setDescription("职位描述");
    job.setPubdate(new Date());
    job.setCloseDate(job.getExpdate());
    return job;
  }

  public static List<JobLocation> createLocationList(int jobId) {
    int locationId = 500;
    List<JobLocation> locations = Lists.newArrayList();
    locations.add(JobLocation.of(jobId, locationId++));
    locations.add(JobLocation.of(jobId, locationId++));
    locations.add(JobLocation.of(jobId, locationId++));
    return locations;
  }

  private static Date createExpireDate(int increment) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, increment);
    return calendar.getTime();
  }
}
