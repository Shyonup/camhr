package com.camhr.employer.account.mapper;

import com.camhr.employer.account.builder.AccountTradeQueryBuider;
import com.camhr.employer.account.entity.Account;
import com.camhr.employer.account.entity.AccountTrade;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

  int addAccount(Account account);

  int addAccountTrade(AccountTrade accountTrade);

  List<AccountTrade> queryTrade(AccountTradeQueryBuider tradeQueryBuider);

  long countTrade(AccountTradeQueryBuider tradeQueryBuider);

  Account getAccount(@Param("employerId") long employerId);

  int changeBalance(@Param("employerId") long employerId, @Param("amount") BigDecimal amount);
}
