package com.camhr.job.samples;

import com.camhr.job.constants.JobApplyType;
import com.camhr.job.entity.JobApply;
import java.util.Date;
import we.lang.Q;

/**
 * Created by Allen on 2018/9/19.
 */
public class JobApplySamples {

  public static JobApply createTestData(long jobId, long seekerId) {
    JobApply jobApply = new JobApply();
    jobApply.setJobId(jobId);
    jobApply.setSeekerId(seekerId);
    jobApply.setEmployerId(Q.now());
    jobApply.setApplyType(JobApplyType.USER_APPLY.value());
    jobApply.setExpdate(new Date());
    jobApply.setCvId(Q.now());
    jobApply.setLetterId(new Long(Q.now()));
    return jobApply;
  }

}
