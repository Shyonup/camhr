package com.camhr.job.mapper;

import com.camhr.job.builders.JobStatisticsBuilder;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.statistics.JobStatusStatistics;
import com.camhr.job.samples.Jobs;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Q;

/**
 * Created by Allen on 2018/9/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JobStatisticsMapperTest {

  @Autowired
  private JobMapper jobMapper;

  @Autowired
  private JobStatisticsMapper jobStatisticsMapper;

  /**
   * 测试数据：
   *    已上线 职位： 3条
   *    下线 职位： 2条
   *    未上线 职位： 1条
   */
  @Test
  public void getEmployerJobStatusStatistics() {
    long employerId = Q.now();
    int quantityOfOnline = 3;
    int quantityOfOffline = 2;
    int quantityOfDraft = 1;

    for (int i = 0; i < quantityOfOnline; i++) {
      Job job = Jobs.createOnlineJob(employerId);
      jobMapper.addJob(job);
      jobMapper.publishJob(job);
    }

    for (int i = 0; i < quantityOfOffline; i++) {
      Job job = Jobs.createOfflineJob(employerId);
      jobMapper.addJob(job);
      jobMapper.publishJob(job);
    }

    for (int i = 0; i < quantityOfDraft; i++) {
      jobMapper.addJob(Jobs.createDraftJob(employerId));
    }

    JobStatisticsBuilder jobStatisticsBuilder = new JobStatisticsBuilder();
    jobStatisticsBuilder.setEmployerId(employerId);
    JobStatusStatistics result = jobStatisticsMapper.getEmployerJobStatusStatistics(jobStatisticsBuilder);
    Assert.assertEquals(quantityOfOnline, result.getOnline());
    Assert.assertEquals(quantityOfOffline, result.getOffline());
    Assert.assertEquals(quantityOfDraft, result.getDraft());
  }

}
