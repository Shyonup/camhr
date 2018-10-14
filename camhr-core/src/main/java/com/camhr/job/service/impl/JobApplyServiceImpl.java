package com.camhr.job.service.impl;

import com.camhr.employer.entity.EmployerJobApplyStatistics;
import com.camhr.job.builders.EmployerJobApplyQueryBuilder;
import com.camhr.job.builders.JobApplyQueryBuilder;
import com.camhr.job.constants.InterviewStatus;
import com.camhr.job.constants.JobApplyStatus;
import com.camhr.job.entity.EmployerJobApply;
import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.config.JobApplyConfiguration;
import com.camhr.job.constants.JobApplyType;
import com.camhr.job.constants.JobStatus;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobApply;
import com.camhr.job.entity.UserJobApply;
import com.camhr.job.error.JobErrorCodes;
import com.camhr.job.mapper.JobApplyMapper;
import com.camhr.job.service.JobApplyService;
import com.camhr.job.service.JobService;
import com.camhr.job.utils.TimeUtil;
import com.camhr.resume.entity.Resume;
import com.camhr.user.error.ResumeErrorCodes;
import com.camhr.user.service.UserResumeService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import we.lang.Exceptions;
import we.util.Page;

/**
 * Created by Allen on 2018/9/19.
 */
@Service
public class JobApplyServiceImpl implements JobApplyService {

  @Autowired
  private JobApplyMapper jobApplyMapper;

  @Autowired
  private JobService jobService;

  @Autowired
  private UserResumeService resumeService;

  @Override
  public int addJobApply(JobApply jobApply) {
    /* 数据校验 */
    // TODO 校验外键数据: letter_id、常量外键的校验
    Job jobFromDB = jobService.getJobBaseInfo(jobApply.getJobId());
    if (jobFromDB == null || jobFromDB.getStatus() != JobStatus.PUBLISHED.value()) {
      Exceptions.of(JobApplyService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }
    Resume resumeFromDB = resumeService.getResumeBaseInfo(jobApply.getCvId());
    if (resumeFromDB == null) {
      Exceptions.of(JobApplyService.class).notFound(ResumeErrorCodes.RESUME_NOT_EXISTS);
    }

    /* 主要操作 */
    jobApply.setEmployerId(jobFromDB.getEmployerId()); // 外键，冗余字段，从Job信息数获取
    jobApply.setApplydate(TimeUtil.getNowDate());
    jobApply.setExpdate(TimeUtil.getMidnightDate(JobApplyConfiguration.JOB_APPLY_VIEW_CV_DAYS)); /*
       设置失效时间，就是说：过了多少天后，这条 职位-简历 信息就作废了。
    */

    int row = 0;
    Long id = jobApplyMapper
        .getJobApplyId(jobApply.getSeekerId(), jobApply.getJobId(), jobApply.getCvId());
    if (id == null) { // 第一次投递简历
      row = jobApplyMapper.addJobApply(jobApply);
    } else { // 重复投递简历
      jobApply.setId(id);
      row = jobApplyMapper.repeatedlyApplyJob(jobApply); // 重复申请就update
    }

    if (row > 0) { // 添加数据成功
      // 如果是用户自主投递职位的话，要刷新 职位申请总数的统计数据
      if (jobApply.getApplyType() == JobApplyType.USER_APPLY.value()) {
        jobService.refreshStatisticOfApplyingJob(jobApply.getJobId());
      }
    }
    return row;
  }

  @Override
  public long countUserJobApplyList(UserJobApplyQueryBuilder jobApplyQueryBuilder) {
    return jobApplyMapper.countUserJobApplyList(jobApplyQueryBuilder);
  }

  @Override
  public UserJobApply getUserApplyJob(long jobApplyId, long seekerId) {
    UserJobApply userApplyJob = getUserApplyJob(jobApplyId);
    if (userApplyJob != null && userApplyJob.getSeekerId() != seekerId) {
      userApplyJob = null; // 不允许查询别人的数据
    }
    return userApplyJob;
  }

  @Override
  public UserJobApply getUserApplyJob(long jobApplyId) {
    return jobApplyMapper.getUserJobApply(jobApplyId);
  }

  @Override
  public int markJobAsCommunicated(long jobId, long seekerId) {
    /* 数据校验 */
    // job信息必须存在，而且是发布状态
    Job jobFromDB = jobService.getJobBaseInfo(jobId);
    if (jobFromDB == null || jobFromDB.getStatus() != JobStatus.PUBLISHED.value()) {
      Exceptions.of(JobApplyService.class).notFound(JobErrorCodes.JOB_NOT_EXIST);
    }
    if (jobApplyMapper.existsJobCommunicated(jobId, seekerId)) {
      return 1; // 数据库已经存在数据了，什么都不用做
    }

    return jobApplyMapper.markJobAsCommunicated(jobId, seekerId); // 不存在就插入数据
  }

  @Override
  public Page<UserJobApply> queryUserJobApplyList(UserJobApplyQueryBuilder jobApplyQueryBuilder) {
    Page<UserJobApply> page = Page.of(jobApplyQueryBuilder);
    page.setResult(jobApplyMapper.queryUserJobApplyList(jobApplyQueryBuilder));
    page.setTotalCount(jobApplyMapper.countUserJobApplyList(jobApplyQueryBuilder));
    return page;
  }

  @Override
  public Page<EmployerJobApply> queryEmployerJobApplyList(
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder) {
    Page<EmployerJobApply> page = Page.of(employerJobApplyQueryBuilder);
    page.setResult(jobApplyMapper.queryEmployerJobApplyList(employerJobApplyQueryBuilder));
    page.setTotalCount(jobApplyMapper.countEmployerJobApplyList(employerJobApplyQueryBuilder));
    return page;
  }

  @Override
  public EmployerJobApplyStatistics getApplicationStatusStatistics(
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder) {
    return jobApplyMapper.getApplicationStatusStatistics(employerJobApplyQueryBuilder);
  }

  @Override
  public int updateJobApplyStatus(long jobApplyId, int status, long userId) {
    return jobApplyMapper.updateJobApplyStatus(jobApplyId, status, userId);
  }

  @Override
  public int updateJobApplyInterview(EmployerJobApply employerJobApply) {
    return jobApplyMapper.updateJobApplyInterview(employerJobApply);
  }

  @Override
  public JobApply viewResumePower(long employerId, long resumeId, long jobApplyId) {
    return jobApplyMapper.viewResumePower(employerId, resumeId, jobApplyId);
  }

  @Override
  public int updateJobApplyInterviewStatus(long jobApplyId, int interviewStatus, long employerId) {
    /* 数据校验 */
    if (!InterviewStatus.allStatus().contains(interviewStatus)) { // 不允许乱传状态过来
      // 乱传就抛异常
      Exceptions.of(JobApplyService.class).error(JobErrorCodes.INTERVIEW_STATUS_IS_NOT_EXIST);
    }

    // 操作数据库
    return jobApplyMapper.updateJobApplyInterviewStatus(jobApplyId, interviewStatus, employerId);
  }

  @Override
  public int batchUpdateStatus(Set<Long> jobApplyIds, int jobApplyStatus, long userId) {
    if (!JobApplyStatus.allStatus().contains(jobApplyStatus)) {
      Exceptions.of(JobApplyService.class).error(JobErrorCodes.JOB_APPLY_STATUS_IS_NOT_EXIST);
    }
    return jobApplyMapper.batchUpdateStatus(jobApplyIds, jobApplyStatus, userId);
  }

  @Override
  public Page<Job> queryJobApplyReplies(JobApplyQueryBuilder jobApplyQueryBuilder) {
    //只要状态不是已申请，其余状态都相当于已经查看
    List<Integer> list = JobApplyStatus.allStatus();
    list.remove(JobApplyStatus.APPLIED.value());
    jobApplyQueryBuilder.setStatus(list);
    Page<Job> page = Page.of(jobApplyQueryBuilder);
    page.setResult(jobApplyMapper.queryJobApplyReplies(jobApplyQueryBuilder));
    page.setTotalCount(jobApplyMapper.countJobApplyReplies(jobApplyQueryBuilder));
    return page;
  }
}
