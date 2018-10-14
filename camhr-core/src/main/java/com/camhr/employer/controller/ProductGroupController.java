package com.camhr.employer.controller;

import com.camhr.employer.product.builder.ProductGroupQueryBuilder;
import com.camhr.employer.product.service.ProductGroupService;
import com.camhr.job.constants.JobLanguage;
import com.camhr.job.entity.Job;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import we.web.Result;

/**
 * 企业端查询职位信息
 *
 * TODO 从登陆信息中获取employerId
 */
@RestController
@RequestMapping("/${version:v1.0.0}/employers/product/services")
public class ProductGroupController {

  @Autowired
  private ProductGroupService productServiceService;

  @ApiOperation(value = "分页查询 可购买的服务包", response = Job.class)
  @GetMapping
  public Result queryJobs(ProductGroupQueryBuilder queryBuilder) {
    queryBuilder.setLanguage(JobLanguage.getJobLanguageByLocale(LocaleContextHolder.getLocale()));
    return Result.data(productServiceService.queryProductGroups(queryBuilder));
  }

}