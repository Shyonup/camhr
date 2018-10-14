package com.camhr.user.controller;


import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.constants.JobApplyType;
import com.camhr.job.entity.JobApply;
import com.camhr.job.service.JobApplyService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

@RestController
@RequestMapping("/${version:v1.0.0}/users/jobs")
@Validated
@PreAuthorize("hasRole('USER')")
public class UserJobController {

  @Autowired
  private JobApplyService jobApplyService;

  @ApiOperation("标记这个职位已经 沟通过了")
  @PutMapping("/{jobId}/communication")
  public Result markJobAsCommunicated(@ApiIgnore @CurrentUser User user, @PathVariable("jobId") long jobId) {

    return Result.of(jobApplyService.markJobAsCommunicated(jobId, user.getUserId()))
        .success("{user.mark.job.as.communicated.success}")
        .fail("{user.mark.job.as.communicated.success}");
  }

  @ApiOperation("申请职位")
  @PostMapping("/{jobId}/applications")
  public Result userApplyJob(@ApiIgnore @CurrentUser User user,
      @PathVariable("jobId") long jobId ,@Valid @RequestBody JobApply jobApply) {
    jobApply.setApplyType(JobApplyType.USER_APPLY.value());
    jobApply.setJobId(jobId);
    jobApply.setSeekerId(user.getUserId());
    return Result.of(jobApplyService.addJobApply(jobApply))
        .success("{user.apply.job.success}")
        .fail("{user.apply.job.fail}");
  }

  @ApiOperation("用户端查询已申请的职位")
  @GetMapping("/applications")
  public Result userApplyJob(@ApiIgnore @CurrentUser User user,
      UserJobApplyQueryBuilder jobApplyQueryBuilder) {
    jobApplyQueryBuilder.setSeekerId(user.getUserId());
    return Result.data(jobApplyService.queryUserJobApplyList(jobApplyQueryBuilder));
  }

  @ApiOperation("用户端查询已申请的职位 （单条）")
  @GetMapping("/applications/{jobApplyId}")
  public Result getUserApplyJob(@ApiIgnore @CurrentUser User user,
      @PathVariable("jobApplyId") long jobApplyId) {
    return Result.data(jobApplyService.getUserApplyJob(jobApplyId, user.getUserId()));
  }

}
