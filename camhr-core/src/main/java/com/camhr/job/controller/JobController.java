package com.camhr.job.controller;


import com.camhr.job.builders.JobQueryBuilder;
import com.camhr.job.constants.JobLanguage;
import com.camhr.job.constants.JobStatus;
import com.camhr.job.service.JobService;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/jobs")
public class JobController {

  @Autowired
  private JobService jobService;

  @ApiOperation("用户端查询职位")
  @GetMapping
  public Result employerQueryJobs(JobQueryBuilder jobQueryBuilder) {
    // 获取语言
    JobLanguage jobLanguage = JobLanguage.getJobLanguageByLocale(LocaleContextHolder.getLocale());
    jobQueryBuilder.setLanguage(jobLanguage.value());
    jobQueryBuilder.setOnline(true); // 必须处于上线状态
    // TODO 从登陆信息中获取seekerId

    return Result.data(jobService.queryJobs(jobQueryBuilder));
  }

  @ApiOperation("单个职位，这个职位给用户端用的，会统计职位被点击的次数")
  @GetMapping("/{jobId}")
  public Result getJob(@PathVariable("jobId") long jobId) {
    return Result.data(jobService.getJob(jobId));
  }

}
