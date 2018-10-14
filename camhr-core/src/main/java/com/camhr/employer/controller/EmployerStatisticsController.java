package com.camhr.employer.controller;

import com.camhr.employer.entity.statistics.EmployerDashboardStatistics;
import com.camhr.employer.service.EmployerStatisticsService;
import com.camhr.job.entity.statistics.JobApplyStatusStatistics;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

/**
 * Created by Allen on 2018/9/12.
 */
@RestController
@RequestMapping("/${version:v1.0.0}/employers/statistics")
public class EmployerStatisticsController {

  @Autowired
  private EmployerStatisticsService employerStatisticsService;

  @ApiOperation(value = "获取 企业端公司首页统计 查询", response = EmployerDashboardStatistics.class)
  @GetMapping("/dashboard")
  public Result dashboardStatistics(@ApiIgnore @CurrentUser User user) {
    // TODO 从登陆信息中获取 employerId
    return Result.data(employerStatisticsService.dashboardStatistics(user.getUserId()));
  }

  @ApiOperation(value = "获取 公司职位的统计数据", response = JobApplyStatusStatistics.class)
  @GetMapping("/jobs/status")
  public Result getEmployerJobStatusStatistics(@ApiIgnore @CurrentUser User user) {
    // TODO 从登陆信息中获取 employerId
    return Result.data(employerStatisticsService.getEmployerJobStatusStatistics(user.getUserId()));
  }

}
