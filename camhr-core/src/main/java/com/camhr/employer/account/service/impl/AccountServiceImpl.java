package com.camhr.employer.account.service.impl;

import com.camhr.employer.account.builder.AccountTradeQueryBuider;
import com.camhr.employer.account.entity.Account;
import com.camhr.employer.account.entity.AccountTrade;
import com.camhr.employer.account.error.AccountErrors;
import com.camhr.employer.account.mapper.AccountMapper;
import com.camhr.employer.account.service.AccountService;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Exceptions;
import we.util.Page;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountMapper accountMapper;


  @Override
  public Page<AccountTrade> queryTrades(AccountTradeQueryBuider tradeQueryBuider) {
    Page<AccountTrade> page = Page.of(tradeQueryBuider);
    page.setResult(accountMapper.queryTrade(tradeQueryBuider));
    page.setTotalCount(accountMapper.countTrade(tradeQueryBuider));
    return page;
  }

  @Override
  @Transactional
  public int addAccountTrade(AccountTrade trade) {
    Account account = accountMapper.getAccount(trade.getEmployerId());
    if (account == null) {// 资金账户不存在
      account = new Account();
      account.setEmployerId(trade.getEmployerId());
      account.setAmount(new BigDecimal(0.00));
      accountMapper.addAccount(account);
    }
    BigDecimal current = account.getAmount();
    BigDecimal balance = current.add(trade.getAmount());
    if (balance.floatValue() < 0) {
      Exceptions.of(AccountService.class).error(AccountErrors.INSUFFICIENT_BALANCE);
    }
    //扣钱
    accountMapper.changeBalance(trade.getEmployerId(), trade.getAmount());
    trade.setBalance(balance);
    //记录
    trade.setCdate(new Date());
    return accountMapper.addAccountTrade(trade);
  }
}
