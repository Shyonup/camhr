package com.camhr.employer.mapper;

import com.camhr.employer.account.entity.Account;
import com.camhr.employer.account.entity.AccountTrade;
import com.camhr.employer.account.mapper.AccountMapper;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountMapperTest {

  @Autowired
  private AccountMapper accountMapper;

  @Autowired
  private EmployerMapper employerMapper;

  private Account account = new Account();

  private AccountTrade trade = new AccountTrade();

  @Test
  public void addAccount() {
    int employerId = employerMapper.getNextSequence();
    account.setEmployerId(employerId);
    account.setAmount(new BigDecimal(1.22));
    int rows = accountMapper.addAccount(account);
    Assert.assertEquals(1, rows);
  }
}
