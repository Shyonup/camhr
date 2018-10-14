package com.camhr.job.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.camhr.constants.ServiceItem;
import com.camhr.employer.constants.EmployerStatus;
import com.camhr.employer.entity.Employer;
import com.camhr.employer.product.constants.EmployerProductItemStatus;
import com.camhr.employer.product.entity.EmployerProductItem;
import com.camhr.employer.product.service.EmployerProductService;
import com.camhr.employer.product.service.ProductService;
import com.camhr.employer.service.EmployerService;
import com.camhr.job.config.JobConfiguration;
import com.camhr.job.constants.JobStatus;
import com.camhr.job.constants.PublishType;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.PublishJob;
import com.camhr.job.mapper.JobMapper;
import com.camhr.job.service.impl.JobServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by Allen on 2018/1/19.
 */
public class JobServiceTest {

  @Mock
  private JobMapper jobMapper;

  @Mock
  private EmployerService employerService;

  @Mock
  private ProductService productService;

  @Mock
  private EmployerProductService employerProductService;

  @InjectMocks
  private JobService jobService = new JobServiceImpl();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void publishJob() {
    // TODO service层逻辑改了，会抛空指针异常
    Job job = new Job();
    job.setId(2114687);
    job.setStatus(JobStatus.DRAFT.value());
    when(jobMapper.getJob(any(long.class))).thenReturn(job);


    Employer employer = new Employer();
    employer.setStatus(EmployerStatus.OK.value());
    when(employerService.getEmployerBaseInfo(any(long.class))).thenReturn(employer);

    EmployerProductItem employerProductItem = new EmployerProductItem();
    employerProductItem.setStatus(EmployerProductItemStatus.OK.value());
    employerProductItem.setEmployerId(employer.getEmployerId());
    employerProductItem.setAmount(999);
    employerProductItem.setItemId(ServiceItem.FREE_BASIC_JOB);
    when(employerProductService.getEmployerProductItem(any(long.class))).thenReturn(employerProductItem);

    PublishJob publishJob = new PublishJob();
    publishJob.setJobId(job.getId());
    publishJob.setEmployerId(job.getEmployerId());

    jobService.publishJob(publishJob);
    Assert.assertEquals(JobStatus.PUBLISHED.value().intValue(), job.getStatus());
    Assert.assertEquals(true, job.isIspublish());
    Assert.assertNotNull(job.getPubdate());
    Assert.assertNotNull(job.getExpdate());
    Assert.assertNotNull(job.getCloseDate());
    Assert.assertEquals(true, job.getPubtype() == PublishType.BASIC_JOB);
    Assert.assertNotNull(job.getUpdatetime());
  }
}
