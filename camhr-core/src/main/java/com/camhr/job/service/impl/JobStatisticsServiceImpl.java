package com.camhr.job.service.impl;

import com.camhr.job.builders.JobStatisticsBuilder;
import com.camhr.job.entity.statistics.JobStatusStatistics;
import com.camhr.job.mapper.JobStatisticsMapper;
import com.camhr.job.service.JobStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2018/9/25.
 */
@Service
public class JobStatisticsServiceImpl implements JobStatisticsService {

  @Autowired
  private JobStatisticsMapper jobStatisticsMapper;

  @Override
  public JobStatusStatistics getEmployerJobStatusStatistics(long employerId) {
    JobStatisticsBuilder jobStatisticsBuilder = new JobStatisticsBuilder();
    jobStatisticsBuilder.setEmployerId(employerId);
    return jobStatisticsMapper.getEmployerJobStatusStatistics(jobStatisticsBuilder);
  }

}
