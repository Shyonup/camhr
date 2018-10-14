package com.camhr.im.mapper;

import com.camhr.constants.AccountType;
import com.camhr.im.entity.IMAccount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import we.lang.Q;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IMAccountMapperTest {

  @Autowired
  private IMAccountMapper accountMapper;

  private IMAccount imAccount = new IMAccount();

  @Test
  public void addAccount() {
    imAccount.setAccessId("ieoeh");
    imAccount.setToken("ksllslls");
    imAccount.setCreateAt(Q.now());
    imAccount.setAccount("834738");
    imAccount.setType(AccountType.EMPLOYER.value());
    int rows = accountMapper.addAccount(imAccount);
    Assert.assertEquals(1, rows);
  }


  @Test
  public void existAccount(){
    addAccount();
    boolean flag = accountMapper.existAccount(imAccount);
    Assert.assertTrue(flag);
  }
}
