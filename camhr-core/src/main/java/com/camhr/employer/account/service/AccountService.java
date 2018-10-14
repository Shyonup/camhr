package com.camhr.employer.account.service;

import com.camhr.employer.account.builder.AccountTradeQueryBuider;
import com.camhr.employer.account.entity.AccountTrade;
import we.util.Page;

public interface AccountService {

  Page<AccountTrade> queryTrades(AccountTradeQueryBuider tradeQueryBuider);

  /**
   * 充值.扣费接口
   * amount 充值+,扣费-
   * @param accountTrade
   * @return
   */
  int addAccountTrade(AccountTrade accountTrade);
}
