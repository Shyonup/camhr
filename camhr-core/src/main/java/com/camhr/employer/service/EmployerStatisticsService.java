package com.camhr.employer.service;

import com.camhr.employer.entity.statistics.EmployerDashboardStatistics;
import com.camhr.job.entity.statistics.JobStatusStatistics;

/**
 * Created by Allen on 2018/9/26.
 */
public interface EmployerStatisticsService {

  /**
   * 获取 企业端公司首页统计 查询
   */
  EmployerDashboardStatistics dashboardStatistics(long employerId);

  /**
   * 获取 公司职位的统计数据
   */
  JobStatusStatistics getEmployerJobStatusStatistics(long employerId);
}
