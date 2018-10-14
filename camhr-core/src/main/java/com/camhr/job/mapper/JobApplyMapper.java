package com.camhr.job.mapper;

import com.camhr.employer.entity.EmployerJobApplyStatistics;
import com.camhr.job.builders.EmployerJobApplyQueryBuilder;
import com.camhr.job.builders.JobApplyQueryBuilder;
import com.camhr.job.entity.EmployerJobApply;
import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobApply;
import com.camhr.job.entity.UserJobApply;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Allen on 2018/9/19.
 */
@Mapper
public interface JobApplyMapper {

  int addJobApply(JobApply jobApply);

  /**
   * 查询JOB_APPLY 表数据的同时，根据前端页面需求，查一些外键数据，（外键数据塞到 UserJobApply 对象里面）
   */
  List<UserJobApply> queryUserJobApplyList(UserJobApplyQueryBuilder jobApplyQueryBuilder);

  long countUserJobApplyList(UserJobApplyQueryBuilder jobApplyQueryBuilder);

  Long getJobApplyId(@Param("seekerId") long seekerId, @Param("jobId") long jobId,
      @Param("cvId") long cvId);

  int repeatedlyApplyJob(JobApply jobApply);

  UserJobApply getUserJobApply(@Param("jobApplyId") long jobApplyId);

  int markJobAsCommunicated(@Param("jobId") long jobId, @Param("seekerId") long seekerId);

  boolean existsJobCommunicated(@Param("jobId") long jobId, @Param("seekerId") long seekerId);

  List<EmployerJobApply> queryEmployerJobApplyList(
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder);

  long countEmployerJobApplyList(EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder);


  EmployerJobApplyStatistics getApplicationStatusStatistics(
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder);

  int updateJobApplyStatus(@Param("jobApplyId") long jobApplyId, @Param("status") int status,
      @Param("userId") long userId);

  int batchUpdateStatus(@Param("jobApplyIds") Set<Long> jobApplyIds,
      @Param("status") int status, @Param("userId") long userId);

  int updateJobApplyInterview(EmployerJobApply employerJobApply);


  JobApply viewResumePower(@Param("employerId") long employerId,
      @Param("resumeId") long resumeId, @Param("jobApplyId") long jobApplyId);

  int updateJobApplyInterviewStatus(@Param("jobApplyId") long jobApplyId,
      @Param("interviewStatus") int interviewStatus,
      @Param("employerId") long employerId);

  List<Job> queryJobApplyReplies(JobApplyQueryBuilder jobApplyQueryBuilder);

  long countJobApplyReplies(JobApplyQueryBuilder jobApplyQueryBuilder);
}
