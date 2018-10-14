package com.camhr.job.service;

import com.camhr.employer.entity.EmployerJobApplyStatistics;
import com.camhr.job.builders.EmployerJobApplyQueryBuilder;
import com.camhr.job.builders.JobApplyQueryBuilder;
import com.camhr.job.entity.EmployerJobApply;
import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobApply;
import com.camhr.job.entity.UserJobApply;
import java.util.Set;
import we.util.Page;

/**
 * Created by Allen on 2018/9/19.
 */
public interface JobApplyService {

  /**
   * Add 一条 JobApply数据到 U_JOB_APPLY
   */
  int addJobApply(JobApply jobApply);

  long countUserJobApplyList(UserJobApplyQueryBuilder jobApplyQueryBuilder);

  UserJobApply getUserApplyJob(long jobApplyId, long seekerId);

  UserJobApply getUserApplyJob(long jobApplyId);

  /**
   * 标记这个职位已经 沟通过了
   */
  int markJobAsCommunicated(long jobId, long seekerId);

  /**
   * 根据前端页面需求，取一些外键数据，并把这些数据存到UserJobApply对象里面
   */

  Page<UserJobApply> queryUserJobApplyList(UserJobApplyQueryBuilder jobApplyQueryBuilder);

  Page<EmployerJobApply> queryEmployerJobApplyList(
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder);

  EmployerJobApplyStatistics getApplicationStatusStatistics(
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder);

  int updateJobApplyStatus(long jobApplyId, int status, long userId);

  int batchUpdateStatus(Set<Long> jobApplyIds, int jobApplyStatus, long userId);

  /**
   * 更改面试邀请的详细信息
   */
  int updateJobApplyInterview(EmployerJobApply employerJobApply);

  JobApply viewResumePower(long employerId, long resumeId, long jobApplyId);

  /**
   * 更改面试的状态
   */
  int updateJobApplyInterviewStatus(long jobApplyId, int interviewStatus, long employerId);

  Page<Job> queryJobApplyReplies(JobApplyQueryBuilder jobApplyQueryBuilder);
}
