package com.camhr.job.mapper;

import com.camhr.job.entity.statistics.JobApplyStatusStatistics;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Allen on 2018/9/25.
 */
@Mapper
public interface JobApplyStatisticsMapper {

  List<JobApplyStatusStatistics> getJobApplyStatusStatisticsByJobIds(@Param("jobIds") Set<Long> jobIds);
}
