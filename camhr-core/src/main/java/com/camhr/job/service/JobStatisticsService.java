package com.camhr.job.service;

import com.camhr.job.entity.statistics.JobStatusStatistics;

/**
 * Created by Allen on 2018/9/25.
 */
public interface JobStatisticsService {

  /**
   * 获取 不同状态（已发布，未发布）的 职位统计数据
   */
  JobStatusStatistics getEmployerJobStatusStatistics(long employerId);

}
