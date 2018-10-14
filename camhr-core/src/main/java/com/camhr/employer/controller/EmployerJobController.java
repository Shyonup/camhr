package com.camhr.employer.controller;

import com.camhr.employer.entity.Employer;
import com.camhr.employer.service.EmployerService;
import com.camhr.job.builders.JobQueryBuilder;
import com.camhr.job.entity.Job;
import com.camhr.job.entity.PublishJob;
import com.camhr.job.entity.TopJob;
import com.camhr.job.service.JobService;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

/**
 * 企业端查询职位信息
 *
 */
@RestController
@RequestMapping("/${version:v1.0.0}/employers/jobs")
@Validated
public class EmployerJobController {

  @Autowired
  private JobService jobService;

  @Autowired
  private EmployerService employerService;

  @ApiOperation("添加职位")
  @PostMapping
  public Result addJob(@ApiIgnore @CurrentUser User user, @Valid @RequestBody Job job,
      HttpServletRequest request) {
    Employer employerBaseInfo = employerService.getEmployerBaseInfo(user.getUserId());
    if (request.getParameter("industrialId") == null) { // 如果前端不传行业Id过来的话
      if (employerBaseInfo != null) {
        int industrialId = employerBaseInfo.getIndustrialId();
        job.setIndustrialId(industrialId <= 0 ? 141 : industrialId); // 获取企业自带的Id，141表示其他
      }
    }
    if (request.getParameter("contactId") == null) { // 如果前端不传联系人Id过来的话
      if (employerBaseInfo != null) {
        job.setContactId(new Long(employerBaseInfo.getContactId())); // 获取企业自带的联系人Id
      }
    }

    job.setEmployerId(user.getUserId());
    return Result.of(jobService.addJob(job))
        .addAttribute("jobId", job.getId())
        .success("{employer.add.job.success}")
        .fail("{employer.add.job.fail}");
  }

  @ApiOperation("删除职位")
  @DeleteMapping("/{jobId}")
  public Result removeJob(@ApiIgnore @CurrentUser User user, @PathVariable("jobId") long jobId) {
    return Result.of(jobService.removeJob(jobId, user.getUserId()))
        .success("{employer.remove.job.success}")
        .fail("{employer.remove.job.fail}");
  }

  @ApiOperation("编辑职位")
  @PutMapping("/{jobId}")
  public Result updateJob(@ApiIgnore @CurrentUser User user, @PathVariable("jobId") long jobId,
      @RequestBody Job job) {
    job.setId(jobId);
    job.setEmployerId(user.getUserId());
    return Result.of(jobService.updateJob(job))
        .success("{employer.update.job.success}")
        .fail("{employer.update.job.fail}");
  }

  @ApiOperation("职位上线")
  @PutMapping("/{jobId}/publish")
  public Result enableJob(@ApiIgnore @CurrentUser User user, @PathVariable("jobId") long jobId,
      @RequestBody PublishJob publishJob) {

    publishJob.setEmployerId(user.getUserId());
    publishJob.setJobId(jobId);
    return Result.of(jobService.publishJob(publishJob))
        .success("{employer.enable.job.success}")
        .fail("{employer.enable.job.fail}");
  }

  @ApiOperation("职位下线")
  @PutMapping("/{jobId}/close")
  public Result disableJob(@ApiIgnore @CurrentUser User user, @PathVariable("jobId") long jobId) {
    return Result.of(jobService.closeJob(Job.of(jobId, user.getUserId())))
        .success("{employer.disable.job.success}")
        .fail("{employer.disable.job.fail}");
  }

  @ApiOperation("职位置顶续费")
  @PutMapping("/{jobId}/top")
  public Result topJob(@ApiIgnore @CurrentUser User user,
      @PathVariable("jobId") long jobId,
      @RequestBody TopJob topJob) {
    topJob.setJobId(jobId);
    topJob.setEmployerId(user.getUserId());
    return Result.of(jobService.topJob(topJob))
        .success("{employer.top.job.success}")
        .fail("{employer.top.job.fail}");
  }

  @ApiOperation("智能刷新职位，body里面存一个 布尔值，格式: \"autoRenew\" : true")
  @PutMapping("/{jobId}/auto/renew")
  public Result autoRenewJobs(@ApiIgnore @CurrentUser User user,
      @Valid @Size(min = 1, max = 1) @RequestBody Map<String, Boolean> autoRenewMap, @PathVariable("jobId") long jobId) {
    Boolean autoRenew = autoRenewMap.get("autoRenew");
    return Result.of(jobService
        .updateJobAutoRenewStatus(jobId, user.getUserId(), autoRenew == null ? false : autoRenew))
        .success("{employer.refresh.job.success}")
        .fail("{employer.refresh.job.fail}");
  }

  @ApiOperation(value = "企业端查询职位", response = Job.class)
  @GetMapping
  public Result queryJobs(@ApiIgnore @CurrentUser User user, JobQueryBuilder jobQueryBuilder) {
    jobQueryBuilder.setEmployerId(user.getUserId());
    return Result.data(jobService.queryEmployerJobs(jobQueryBuilder));
  }

  @ApiOperation(value = "企业端查询单个职位", response = Job.class)
  @GetMapping("/{jobId}")
  public Result getJob(@ApiIgnore @CurrentUser User user, @PathVariable("jobId") long jobId) {
    return Result.data(jobService.getJob(jobId, user.getUserId()));
  }

}