package com.camhr.employer.service;

import com.camhr.employer.builder.ResumeQueryBuilder;
import com.camhr.employer.entity.ResumeInfo;
import com.camhr.resume.entity.Resume;
import we.util.Page;

public interface EmployerResumeService {

  Page<ResumeInfo> queryEmployerResumeInfo(ResumeQueryBuilder resumeQueryBuilder);

  Resume resumeView(long employerId, long resumeId, long jobApplyId);

  Resume resumeView(long resumeId);

  int addEemployerHits(long resumeId);
}
