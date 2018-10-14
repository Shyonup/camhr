package com.camhr.employer.service.impl;

import com.camhr.employer.builder.ResumeQueryBuilder;
import com.camhr.employer.entity.ResumeInfo;
import com.camhr.employer.events.ViewResumeEvent;
import com.camhr.employer.mapper.EmployerResumeMapper;
import com.camhr.employer.service.EmployerResumeService;
import com.camhr.job.constants.JobApplyStatus;
import com.camhr.job.entity.JobApply;
import com.camhr.job.service.JobApplyService;
import com.camhr.resume.entity.Resume;
import com.camhr.resume.entity.Seeker;
import com.camhr.user.error.ResumeErrorCodes;
import com.camhr.user.service.UserResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import we.lang.Exceptions;
import we.util.Page;

@Service
public class EmployerResumeServiceImpl implements EmployerResumeService {

  @Autowired
  private EmployerResumeMapper employerResumeMapper;

  @Autowired
  private UserResumeService userResumeService;

  @Autowired
  private JobApplyService jobApplyService;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public Page<ResumeInfo> queryEmployerResumeInfo(ResumeQueryBuilder resumeQueryBuilder) {
    Page<ResumeInfo> page = Page.of(resumeQueryBuilder);
    page.setResult(employerResumeMapper.queryResumeInfoList(resumeQueryBuilder));
    page.setTotalCount(employerResumeMapper.countResumeInfoList(resumeQueryBuilder));
    return page;
  }

  /**
   * 企业端查看简历
   */
  @Override
  public Resume resumeView(long employerId, long resumeId, long jobApplyId) {
    Resume resume = userResumeService.getResumeByResumeId(resumeId);
    if (resume == null) {
      Exceptions.of(UserResumeService.class).notFound(ResumeErrorCodes.RESUME_NOT_EXISTS);
    }
    //简历点击次数加1
    applicationEventPublisher.publishEvent(new ViewResumeEvent(resumeId));
    JobApply jobApply = jobApplyService.viewResumePower(employerId, resumeId, jobApplyId);
    //用户没有投递且企业没有购买，就没有权限查询，就要将联系方式屏蔽掉
    if (jobApply == null) {
      Seeker seeker = resume.getProfile();
      seeker.setMobile("*********");
      seeker.setEmail("*********");
      seeker.setAddress("*********");
    } else {  //有查看简历的权限
      if (JobApplyStatus.APPLIED.equals(jobApply.getStatus())) {
        //如果职位申请的状态为已投递，那么就将状态改为已查看
        jobApplyService
            .updateJobApplyStatus(jobApply.getId(), JobApplyStatus.VIEWED.value(), employerId);
      }
    }
    return resume;
  }

  @Override
  public Resume resumeView(long resumeId) {
    Resume resume = userResumeService.getResumeByResumeId(resumeId);
    if (resume == null) {
      Exceptions.of(UserResumeService.class).notFound(ResumeErrorCodes.RESUME_NOT_EXISTS);
    }
    applicationEventPublisher.publishEvent(new ViewResumeEvent(resumeId));
    Seeker seeker = resume.getProfile();
    seeker.setMobile("*********");
    seeker.setEmail("*********");
    seeker.setAddress("*********");
    return resume;
  }

  /**
   * 简历点击次数加1
   */
  @Override
  public int addEemployerHits(long resumeId) {
    return employerResumeMapper.addEemployerHits(resumeId);
  }
}
