package com.camhr.job.service;

import com.camhr.job.builders.JobQueryBuilder;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.PublishJob;
import com.camhr.job.entity.TopJob;
import java.util.List;
import we.util.Page;

/**
 * Created by Allen on 2018/9/10.
 */
public interface JobService {

  Page<Job> queryJobs(JobQueryBuilder jobQueryBuilder);

  int addJob(Job job);

  int removeJob(long jobId, long employerId);

  int updateJob(Job job);

  /**
   * 获取单条数据，会同时把外键部分数据拿出来。还会返回 面试人数相关的统计信息
   */
  Job getJob(long jobId, long employer);

  /**
   * 获取单条数据，会同时把外键部分数据拿出来
   */
  Job getJob(long jobId);

  /**
   * 刷新职位已报名的成员数量
   */
  int refreshStatisticOfApplyingJob(long jobId);

  /**
   * 不查外键数据，只查E_JOB表的数据
   */
  Job getJobBaseInfo(long jobId);

  /**
   * 发布职位（上线）
   */
  int publishJob(PublishJob publishJob);

  /**
   * 关闭职位（下线）
   */
  int closeJob(Job job);

  /**
   * 获取 职位的 面试人数统计数据
   */
  void fetchJobIntervieweeStatistics(List<Job> jobList);

  /**
   * 企业查询自家的职位信息，带统计信息
   */
  Page<Job> queryEmployerJobs(JobQueryBuilder jobQueryBuilder);

  /**
   * 更新 Job表的 autoRenew字段
   * @param isAutoRenew 职位是否 过期自动续费
   * @param employerId 企业Id
   * @return
   */
  int updateJobAutoRenewStatus(long jobId, long employerId, boolean isAutoRenew);

  /**
   * 查找需要 自动续费的职位数据
   */
  List<PublishJob> findJobsWhichNeedToAutoRenew();

  /**
   * 把 已过期的 职位，改成过期状态
   * @return
   */
  int closeExpiredJobs();

  /**
   * 职位置顶
   */
  int topJob(TopJob topJob);

  int downTheTopJobsWhichIsExpired();
}
