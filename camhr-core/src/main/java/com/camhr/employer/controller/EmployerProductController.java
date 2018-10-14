package com.camhr.employer.controller;

import com.camhr.employer.product.builder.ProductItemQueryBuilder;
import com.camhr.employer.product.entity.EmployerBuyProduct;
import com.camhr.employer.product.entity.EmployerProductItem;
import com.camhr.employer.product.service.EmployerProductService;
import com.camhr.job.constants.JobLanguage;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

/**
 * Created by Allen on 2018/10/8.
 */
@RestController
@RequestMapping("/${version:v1.0.0}/employers/products")
@Validated
public class EmployerProductController {

  @Autowired
  private EmployerProductService employerProductService;

  @ApiOperation(value = "企业端查询 已购买的服务包", response = EmployerProductItem.class, responseContainer = "List")
  @GetMapping
  public Result queryEmployerProducts(@ApiIgnore @CurrentUser User user,
      ProductItemQueryBuilder queryBuilder, HttpServletRequest request) {
    queryBuilder
        .setLanguage(JobLanguage.getJobLanguageByLocale(LocaleContextHolder.getLocale()));
    queryBuilder.setEmployerId(user.getUserId());

    // 去掉queryBuilder里面的默认值
    if (request.getParameter("status") == null) {
      queryBuilder.setStatus(Collections.EMPTY_LIST);
    }
    if (request.getParameter("minAmount") == null) {
      queryBuilder.setMinAmount(null);
    }
    if (request.getParameter("itemIds") == null) {
      queryBuilder.setItemIds(Collections.EMPTY_LIST);
    }
    if (request.getParameter("mustNotExpired") == null) {
      queryBuilder.setMustNotExpired(false);
    }

    return Result.data(employerProductService.queryEmployerProducts(queryBuilder));
  }

  @ApiOperation(value = "企业端查询 已购买的服务项", response = EmployerProductItem.class, responseContainer = "List")
  @GetMapping("/items")
  public Result queryEmployerProductItems(@ApiIgnore @CurrentUser User user,
      ProductItemQueryBuilder productItemQueryBuilder) {
    productItemQueryBuilder
        .setLanguage(JobLanguage.getJobLanguageByLocale(LocaleContextHolder.getLocale()));
    productItemQueryBuilder.setEmployerId(user.getUserId());
    return Result.data(employerProductService.queryEmployerProductItems(productItemQueryBuilder));
  }

  @ApiOperation(value = "企业购买服务包")
  @PostMapping("/purchase")
  public Result employerBuyProduct(@ApiIgnore @CurrentUser User user, @RequestBody
      @Valid EmployerBuyProduct employerBuyProduct) {
    employerBuyProduct.setEmployerId(user.getUserId());
    return Result.of(employerProductService.employerBuyProduct(employerBuyProduct))
        .success("{employer.buy.product.success}")
        .fail("{employer.buy.product.fail}");
  }

}
