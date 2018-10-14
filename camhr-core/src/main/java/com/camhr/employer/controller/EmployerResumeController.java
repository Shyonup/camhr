package com.camhr.employer.controller;

import com.camhr.employer.builder.ResumeQueryBuilder;
import com.camhr.employer.entity.ResumeInfo;
import com.camhr.employer.service.EmployerResumeService;
import com.camhr.job.builders.EmployerJobApplyQueryBuilder;
import com.camhr.job.builders.UserJobApplyQueryBuilder;
import com.camhr.job.entity.EmployerJobApply;
import com.camhr.job.entity.UserJobApply;
import com.camhr.job.service.JobApplyService;
import com.camhr.resume.entity.Resume;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/employers")
@Validated
public class EmployerResumeController {

  private final String STATISTICS = "statistics";  //用于存放统计简历四种状态数量的map

  @Autowired
  private JobApplyService jobApplyService;

  @Autowired
  private EmployerResumeService employerResumeService;

  @PutMapping("/applications/{jobApplyId}/interview/status/{interviewStatus}")
  @ApiOperation(value = "变更面试状态")
  public Result updateInterviewStatus(@ApiIgnore @CurrentUser User user,
      @PathVariable("jobApplyId") long jobApplyId,
      @PathVariable("interviewStatus") int interviewStatus) {

    return Result.of(jobApplyService
        .updateJobApplyInterviewStatus(jobApplyId, interviewStatus, user.getUserId()))
        .success("{update.jobApply.interview.status.success}")
        .fail("{update.jobApply.interview.status.fail}");
  }

  /**
   * 所有的employerId都从用户登录信息中拿 申请该企业职位的简历列表查询，全部，投递，与下载公用该方法
   */
  @GetMapping("/applications")
  @ApiOperation(value = "企业查询收到的简历")
  public Result queryResumes(@ApiIgnore @CurrentUser User user,
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder) {
    /*如果不是全部查询，需要返回沟通状态的统计数据*/
    employerJobApplyQueryBuilder.setEmployerId(user.getUserId());
    return Result.data(jobApplyService.queryEmployerJobApplyList(employerJobApplyQueryBuilder));
  }

  @GetMapping("/applications/statistics")
  @ApiOperation(value = "查询各个筛选状态的数量")
  public Result getApplicationStatusStatistics(@ApiIgnore @CurrentUser User user,
      EmployerJobApplyQueryBuilder employerJobApplyQueryBuilder) {
    employerJobApplyQueryBuilder.setEmployerId(user.getUserId());
    return Result
        .data(jobApplyService.getApplicationStatusStatistics(employerJobApplyQueryBuilder));
  }

  /**
   * 下载简历,暂时不返回word
   */

//  @GetMapping("/{resumeId}/download")
//  @ApiOperation(value = "下载简历，暂时未完成")
//  public Result downloadResume( @PathVariable("resumeId") int resumeId) {
//    return Result.data(getResume());
//  }


  /**
   * 推荐与人才搜索共用
   */

  @GetMapping("/resumes/search")
  @ApiOperation(value = "推荐与人才搜索共用", response = ResumeInfo.class)
  public Result searchResume(@ApiIgnore @CurrentUser User user,
      ResumeQueryBuilder resumeQueryBuilder) {
    resumeQueryBuilder.setEmployerId(user.getUserId());
    //TODO 未处理搜索字段学历 未返回手机号码 邮箱
    //未返回搜索人才中的当前工作与当前职位
    return Result.data(employerResumeService.queryEmployerResumeInfo(resumeQueryBuilder));
  }

  /**
   * 面试邀请记录
   */
  @GetMapping("/applications/interviews")
  @ApiOperation(value = "面试邀请记录", response = UserJobApply.class, responseContainer = "List")
  public Result interviewRecord(@ApiIgnore @CurrentUser User user,
      UserJobApplyQueryBuilder jobApplyQueryBuilder) {
    jobApplyQueryBuilder.setEmployerId(user.getUserId());
    return Result.data(jobApplyService.queryUserJobApplyList(jobApplyQueryBuilder));
  }

  /**
   * 待沟通，不合适状态更改
   */
  @PutMapping("/{jobApplyId}/status/{jobApplyStatus}")
  @ApiOperation(value = "更改申请记录的状态")
  public Result interviewSchedules(@ApiIgnore @CurrentUser User user,
      @PathVariable("jobApplyId") int jobApplyId,
      @PathVariable("jobApplyStatus") int jobApplyStatus) {
    return Result
        .of(jobApplyService.updateJobApplyStatus(jobApplyId, jobApplyStatus, user.getUserId()))
        .success("{application.status.update.success}")
        .fail("{application.status.update.fail}");
  }

  @PutMapping("status/{jobApplyStatus}")
  @ApiOperation(value = "批量更改申请记录状态，待沟通，不合适")
  public Result batchUpdateStatus(@ApiIgnore @CurrentUser User user,
      @Valid @RequestBody @Size(min = 1) Set<Long> jobApplyIds,
      @PathVariable("jobApplyStatus") int jobApplyStatus) {
    return Result
        .of(jobApplyService
            .batchUpdateStatus(jobApplyIds, jobApplyStatus, user.getUserId()))
        .success("{application.status.update.success}")
        .fail("{application.status.update.fail}");
  }

  /**
   * 邀请面试
   */
  @PutMapping("/{jobApplyId}/interview")
  @ApiOperation(value = "邀请面试")
  public Result interviewSchedule(@PathVariable("jobApplyId") int jobApplyId,
      @ApiIgnore @CurrentUser User user,
      EmployerJobApply employerJobApply) {
    employerJobApply.setId(jobApplyId);
    employerJobApply.setEmployerId(user.getUserId());
    return Result.of(jobApplyService.updateJobApplyInterview(employerJobApply))
        .success("{interview.update.success}").fail("{interview.update.fail}");
  }

  @GetMapping("/resumes/{resumeId}")
  @ApiOperation(value = "简历查看", response = Resume.class)
  public Result resumeView(@ApiIgnore @CurrentUser User user,
      long jobApplyId,
      @PathVariable("resumeId") long resumeId) {
    if (jobApplyId > 0) {
      return Result.data(employerResumeService.resumeView(user.getUserId(), resumeId, jobApplyId));
    } else {
      return Result.data(employerResumeService.resumeView(resumeId));
    }

  }
}
