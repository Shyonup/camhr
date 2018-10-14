package com.camhr.job.mapper;

import com.camhr.job.builders.JobQueryBuilder;
import com.camhr.job.constants.JobStatus;
import com.camhr.job.constants.PublishType;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobLocation;
import com.camhr.job.samples.Jobs;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Q;

/**
 * Created by Allen on 2018/9/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JobMapperTest {

  @Autowired
  private JobMapper jobMapper;

  /**
   * JobQueryBuilder支持的查询条件：
   *    employerId
   *    company
   *    jobTitle
   *    jobTitleOrCompany
   *    locationsId
   */
  @Test
  public void queryJobs() {
    String title_1 =  UUID.randomUUID().toString();
    String title_2 =  UUID.randomUUID().toString();
    int quantity = 3;

    // 添加3条title_1
    for (int i = 0; i < quantity; i++) {
      Job job = Jobs.createJob(title_1);
      job.setEmployerId(Jobs.employId);
      jobMapper.addJob(job);
    }
    // 添加1条title_1
    Job job = Jobs.createJob(title_2);
    job.setEmployerId(Jobs.employId);
    jobMapper.addJob(job);

    JobQueryBuilder jobQueryBuilder = new JobQueryBuilder();
    jobQueryBuilder.setEmployerId(Jobs.employId);
    jobQueryBuilder.setJobTitle(title_1);
    List<Job> result = jobMapper.queryJobs(jobQueryBuilder);
    Assert.assertEquals(quantity, result.size());
  }

  @Test
  public void countJobs() {
    int quantity = 3; // 添加多少条测试数据到数据库
    for (int i = 0; i < quantity; i++) {
      Job job = Jobs.createJob("countJobs");
      job.setEmployerId(Jobs.employId);
      jobMapper.addJob(job);
    }

    JobQueryBuilder jobQueryBuilder = new JobQueryBuilder();
    jobQueryBuilder.setEmployerId(Jobs.employId);
    long result = jobMapper.countJobs(jobQueryBuilder);
    Assert.assertEquals(quantity, result);
  }

  @Test
  public void findJobLocationsByJobIds() {
    long jobId_01 = -Q.now();
    long jobId_02 = jobId_01 + 1;
    long jobId_03 = jobId_01 + 2;
    List<JobLocation> jobLocations = Lists.newArrayList();
    jobLocations.add(JobLocation.of(jobId_01, 500));
    jobLocations.add(JobLocation.of(jobId_02, 500));
    jobLocations.add(JobLocation.of(jobId_03, 500));
    jobMapper.addJobLocations(jobLocations); // 测试数据添加到数据库

    // 查出来看对不对
    Set<Long> jobIds = Sets.newHashSet();
    jobIds.add(jobId_01);
    jobIds.add(jobId_02);
    jobIds.add(jobId_03);
    List<JobLocation> result = jobMapper.findJobLocations(jobIds);
    Assert.assertEquals(jobIds.size(), result.size());
  }

  @Test
  public void deleteJob() {
    Job job = Jobs.createJob("测试职位");
    jobMapper.addJob(job);

    int row = jobMapper.deleteJob(job.getId(), job.getEmployerId());
    Assert.assertEquals(1 ,row);
  }

  @Test
  public void addJobLocations() {
    int jobId = Q.now();
    List<JobLocation> locationList = Jobs.createLocationList(jobId);
    int rows = jobMapper.addJobLocations(locationList);
    Assert.assertEquals(locationList.size(), rows);
  }

  @Test
  public void deleteJobLocations() {
    int jobId = Q.now();
    List<JobLocation> locationList = Jobs.createLocationList(jobId);
    jobMapper.addJobLocations(locationList);
    int rows = jobMapper.deleteJobLocations(jobId);
    Assert.assertEquals(locationList.size(), rows);
  }

  @Test
  public void removeJob() {
    Job job = Jobs.createJob("测试职位");
    job.setStatus(JobStatus.CLOSED.value());
    jobMapper.addJob(job);

    int row = jobMapper.removeJob(job.getId(), job.getEmployerId());
    Assert.assertEquals(1 ,row);
    Integer jobStatusFromDB = jobMapper
        .getJobStatusByEmployerId(job.getId(), job.getEmployerId());
    Assert.assertEquals(JobStatus.REMOVED.value(), jobStatusFromDB);
  }

  @Test
  public void closeJob() {
    Job job = Jobs.createJob("测试职位");
    job.setStatus(JobStatus.PUBLISHED.value());
    jobMapper.addJob(job);

    int row = jobMapper.closeJob(job);
    Assert.assertEquals(1 ,row);
    Integer jobStatusFromDB = jobMapper
        .getJobStatusByEmployerId(job.getId(), job.getEmployerId());
    Assert.assertEquals(JobStatus.CLOSED.value(), jobStatusFromDB);
  }

  @Test
  public void addJob() {
    Job job = Jobs.createJob("测试职位");
    int row = jobMapper.addJob(job);
    Assert.assertEquals(1, row);
  }

  @Test
  public void publishJob() {
    // 先草稿状态，未发布的
    Job job = Jobs.createUnPublishJob("测试发布职位");
    jobMapper.addJob(job);
    Jobs.changeJobInfoToPublish(job);
    int row = jobMapper.publishJob(job);
    Assert.assertEquals(1, row);

    // 把数据get出来，看对不对
    Job result = jobMapper.getJob(job.getId());
    Assert.assertEquals(JobStatus.PUBLISHED.value().intValue(), result.getStatus());
    Assert.assertEquals(true, result.isIspublish());
    Assert.assertNotNull(result.getPubdate());
    Assert.assertNotNull(result.getExpdate());
    Assert.assertNotNull(result.getCloseDate());
    Assert.assertEquals(true, result.getPubtype() == PublishType.BASIC_JOB);
    Assert.assertNotNull(result.getUpdatetime());
  }

  @Test
  public void getJobStatus() {
    // 正常状态下的数据
    {
      Job job = Jobs.createJob("测试发布职位");
      job.setStatus(JobStatus.DRAFT.value());
      jobMapper.addJob(job);
      Integer result = jobMapper.getJobStatusByEmployerId(job.getId(), job.getEmployerId());
      Assert.assertEquals(JobStatus.DRAFT.value(), result);
    }

    {
      Job job = Jobs.createJob("测试发布职位");
      job.setStatus(JobStatus.PUBLISHED.value());
      jobMapper.addJob(job);
      Integer result = jobMapper.getJobStatusByEmployerId(job.getId(), job.getEmployerId());
      Assert.assertEquals(JobStatus.PUBLISHED.value(), result);
    }

    // 查不到数据时
    {
      Integer result = jobMapper.getJobStatusByEmployerId(-Q.now(), -65512);
      Assert.assertEquals(null, result);
    }
  }

}
