package com.camhr.job.mapper;

import com.camhr.job.builders.JobQueryBuilder;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobLocation;
import com.camhr.job.entity.PublishJob;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JobMapper {

  int addJob(Job job);

  Job getJob(@Param("jobId") long jobId);

  List<Job> queryJobs(JobQueryBuilder jobQueryBuilder);

  long countJobs(JobQueryBuilder jobQueryBuilder);

  int publishJob(Job job);

  Integer getJobStatusByEmployerId(@Param("jobId") long jobId, @Param("employerId") long employerId);

  int deleteJob(@Param("jobId") long jobId, @Param("employerId") long employerId);

  int deleteJobLocations(@Param("jobId") long jobId);

  int deleteJobLanguage(@Param("jobId") long jobId);

  int removeJob(@Param("jobId") long jobId, @Param("employerId") long employerId);

  int closeJob(Job job);

  int updateJob(Job job);

  int addJobLocations(@Param("locations") List<JobLocation> locations);

  List<JobLocation> findJobLocations(@Param("jobIds") Set<Long> jobIds);

  /**
   * 刷新职位已报名的成员数量
   */
  int refreshStatisticOfApplyingJob(@Param("jobId") long jobId);

  int updateJobAutoRenewStatus(@Param("jobId") long jobId, @Param("employerId") long employerId,
      @Param("isAutoRenew") boolean isAutoRenew);

  /**
   * 查找需要 自动续费的职位数据
   *
   * 借助JobQueryBuilder，是因为判断职位过期的逻辑都在JobQueryBuilder里面
   */
  List<PublishJob> findJobsWhichNeedToAutoRenew(JobQueryBuilder jobQueryBuilder);

  /**
   * 获取上一次发布职位时，所使用的服务包ID
   */
  Long getEmployerProductItemIdWhenLastPublishJob(@Param("jobId") long jobId);

  int closeExpiredJobs(@Param("nowDate") Date nowDate);

  /**
   * 职位置顶
   */
  int topJob(@Param("jobId") long jobId, @Param("employerId") long employerId,
      @Param("topServiceExpiredDate") Date topServiceExpiredDate);

  int downTheTopJobsWhichIsExpired(@Param("nowDate") Date nowDate);
}
