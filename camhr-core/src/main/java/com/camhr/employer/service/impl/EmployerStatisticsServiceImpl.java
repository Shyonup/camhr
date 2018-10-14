package com.camhr.employer.service.impl;

import com.camhr.employer.entity.statistics.EmployerDashboardStatistics;
import com.camhr.employer.service.EmployerStatisticsService;
import com.camhr.job.builders.JobApplyQueryBuilder;
import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.constants.InterviewStatus;
import com.camhr.job.constants.JobApplyStatus;
import com.camhr.job.entity.statistics.JobStatusStatistics;
import com.camhr.job.service.JobApplyService;
import com.camhr.job.service.JobStatisticsService;
import com.camhr.job.utils.TimeUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2018/9/26.
 */
@Service
public class EmployerStatisticsServiceImpl implements EmployerStatisticsService {

  @Autowired
  private JobApplyService jobApplyService;

  @Autowired
  private JobStatisticsService jobStatisticsService;

  @Override
  public EmployerDashboardStatistics dashboardStatistics(long employerId) {
    EmployerDashboardStatistics dashboardStatistics = new EmployerDashboardStatistics();

    // 获取最近7天的统计数据
    {
      UserJobApplyQueryBuilder jobApplyQueryBuilder = new UserJobApplyQueryBuilder();
      jobApplyQueryBuilder.setApplyStartTime(TimeUtil.getMidnightDate(-6)); // 6天前的0晨0点
      jobApplyQueryBuilder.setApplyEndTime(TimeUtil.getMidnightDate(1)); // 明天的0晨0点
      jobApplyQueryBuilder.setEmployerId(employerId);
      long totalJobApply = jobApplyService.countUserJobApplyList(jobApplyQueryBuilder); // 从其他服务获取数据
      dashboardStatistics.setRecentJobApply(new Long(totalJobApply).intValue()); // set到统计数据的结果集里面
    }

    // 获待面试的数量
    {
      UserJobApplyQueryBuilder jobApplyQueryBuilder = new UserJobApplyQueryBuilder();
      jobApplyQueryBuilder.setEmployerId(employerId);
      jobApplyQueryBuilder.setStatus(Lists.newArrayList(JobApplyStatus.INVITED.value())); // 已经邀请面试了
      jobApplyQueryBuilder.setInterviewStatus(InterviewStatus.NEVER.value()); // 但还没有面试
      long totalJobApply = jobApplyService.countUserJobApplyList(jobApplyQueryBuilder); // 从其他服务获取数据
      dashboardStatistics.setWaitToInterview(new Long(totalJobApply).intValue()); // set到统计数据的结果集里面
    }

    return dashboardStatistics;
  }

  @Override
  public JobStatusStatistics getEmployerJobStatusStatistics(long employerId) {
    return jobStatisticsService.getEmployerJobStatusStatistics(employerId);
  }
}
