package com.camhr.job.mapper;

import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.constants.InterviewStatus;
import com.camhr.job.constants.JobApplyType;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.JobApply;
import com.camhr.job.entity.UserJobApply;
import com.camhr.job.samples.JobApplySamples;
import com.camhr.job.samples.Jobs;
import java.util.List;
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
public class JobApplyMapperTest {

  @Autowired
  private JobApplyMapper jobApplyMapper;

  @Autowired
  private JobMapper jobMapper;

  @Test
  public void updateJobApplyInterviewStatus() {
    long jobId = 12321141;
    long seekerId = 541354;
    long employerId = 6231451;

    JobApply jobApply = JobApplySamples.createTestData(jobId, seekerId);
    jobApply.setEmployerId(employerId);
    jobApplyMapper.addJobApply(jobApply);

    // 数据校验
    // Step_1 先看看插入到数据的数据是样的
    {
      UserJobApply result = jobApplyMapper.getUserJobApply(jobApply.getId());
      Assert.assertEquals(result.getInterviewStatus(), InterviewStatus.NONE.value().intValue());
    }
    // Step_2 修改一下状态，然后查出来看看是什么
    {
      int status = InterviewStatus.VIEWED.value();
      jobApplyMapper.updateJobApplyInterviewStatus(jobApply.getId(), status,
          jobApply.getEmployerId());
      UserJobApply result = jobApplyMapper.getUserJobApply(jobApply.getId());
      Assert.assertEquals(result.getInterviewStatus(), status);
    }
  }

  @Test
  public void existsJobCommunicated() {
    long jobId = -555;
    long seekerId = -666;
    int row = jobApplyMapper.markJobAsCommunicated(jobId, seekerId);
    Assert.assertEquals(1, row);
    boolean flag = jobApplyMapper.existsJobCommunicated(jobId, seekerId);
    Assert.assertEquals(true, flag);
  }

  @Test
  public void queryJobApplyList() {
    long jobId = 5555555555L;
    long seekerId = 666666666L;

    // 测试查询条件：seekId
    {
      seekerId++;
      int quantity = 5;
      for (int i = 0; i < quantity; i++) {
        jobApplyMapper.addJobApply(JobApplySamples.createTestData(jobId++, seekerId));
      }
      jobApplyMapper.addJobApply(JobApplySamples.createTestData(jobId++, seekerId + 1));

      UserJobApplyQueryBuilder jobApplyQueryBuilder = new UserJobApplyQueryBuilder();
      jobApplyQueryBuilder.setSeekerId(seekerId);
      List<UserJobApply> result = jobApplyMapper.queryUserJobApplyList(jobApplyQueryBuilder);
      Assert.assertEquals(quantity, result.size());
    }

    // 测试查询条件：seekId + jobApplyType
    {
      seekerId++;
      int quantity = 5;
      for (int i = 0; i < quantity; i++) {
        JobApply jobApply = JobApplySamples.createTestData(jobId++, seekerId);
        jobApply.setApplyType(JobApplyType.EMPLOYER_FIND.value());
        jobApplyMapper.addJobApply(jobApply);
      }
      JobApply jobApply_2 = JobApplySamples.createTestData(jobId++, seekerId);
      jobApply_2.setApplyType(JobApplyType.USER_APPLY.value());
      jobApplyMapper.addJobApply(jobApply_2);

      UserJobApplyQueryBuilder jobApplyQueryBuilder = new UserJobApplyQueryBuilder();
      jobApplyQueryBuilder.setSeekerId(seekerId);
      jobApplyQueryBuilder.setJobApplyType(JobApplyType.EMPLOYER_FIND.value());
      List<UserJobApply> result = jobApplyMapper.queryUserJobApplyList(jobApplyQueryBuilder);
      Assert.assertEquals(quantity, result.size());
    }
  }

  @Test
  public void refreshStatisticOfApplyingJob() {
    // 添加一条职位
    Job job = Jobs.createJob("测试刷新申请人数");
    job.setTotalApply(0);
    job.setNewApply(0);
    jobMapper.addJob(job);

    // 添加几条申请职位的数据，并调用刷新统计数据的接口
    int quantity = 5; // 申请的职位的记录数
    for (int i = 0; i < quantity; i++) {
      jobApplyMapper.addJobApply(JobApplySamples.createTestData(job.getId(), Q.now()));
    }
    jobMapper.refreshStatisticOfApplyingJob(job.getId());

    // 把统计数据查出来看对不对。
    Job result = jobMapper.getJob(job.getId());
    Assert.assertEquals(quantity, result.getTotalApply());
    Assert.assertEquals(quantity, result.getNewApply());
  }

  @Test
  public void addJobApply() {
    long jobId = 5555555555L;
    long seekerId = 666666666L;
    JobApply jobApply = JobApplySamples.createTestData(jobId, seekerId);
    int row = jobApplyMapper.addJobApply(jobApply);
    Assert.assertEquals(1, row);
  }

}
