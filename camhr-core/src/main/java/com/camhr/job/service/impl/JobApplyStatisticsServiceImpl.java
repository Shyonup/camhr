package com.camhr.job.service.impl;

import com.camhr.job.entity.statistics.JobApplyStatusStatistics;
import com.camhr.job.mapper.JobApplyStatisticsMapper;
import com.camhr.job.service.JobApplyStatisticsService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 简历 - 职位 关联关系 相关的统计数据
 *
 * Created by Allen on 2018/9/25.
 * </pre>
 */
@Service
public class JobApplyStatisticsServiceImpl implements JobApplyStatisticsService {

  @Autowired
  private JobApplyStatisticsMapper jobApplyStatisticsMapper;

  @Override
  public List<JobApplyStatusStatistics> getJobApplyStatusStatisticsByJobIds(Set<Long> jobIds) {
    return jobApplyStatisticsMapper.getJobApplyStatusStatisticsByJobIds(jobIds);
  }
}
