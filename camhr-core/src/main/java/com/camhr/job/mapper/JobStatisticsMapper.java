package com.camhr.job.mapper;

import com.camhr.job.builders.JobStatisticsBuilder;
import com.camhr.job.entity.statistics.JobStatusStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobStatisticsMapper {

  JobStatusStatistics getEmployerJobStatusStatistics(JobStatisticsBuilder jobStatisticsBuilder);
}
