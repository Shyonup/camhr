package com.camhr.employer.account.controller;

import com.camhr.employer.account.builder.AccountTradeQueryBuider;
import com.camhr.employer.account.entity.AccountTrade;
import com.camhr.employer.account.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.security.annotation.CurrentUser;
import we.security.rbac.SimpleUser;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/employers/wallets/trades")
public class EmployerAccountController {

  @Autowired
  private AccountService accountService;

  @ApiOperation(value = "消费记录查询", response = AccountTrade.class)
  @GetMapping()
  public Result queryAccountTrades(@ApiIgnore @CurrentUser SimpleUser simpleUser,
      AccountTradeQueryBuider tradeQueryBuider) {
    tradeQueryBuider.setEmployerId(simpleUser.getUserId());
    return Result.data(accountService.queryTrades(tradeQueryBuider));
  }

  
}
