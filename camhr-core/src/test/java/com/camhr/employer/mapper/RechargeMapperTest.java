package com.camhr.employer.mapper;

import com.camhr.employer.wallet.constants.PaymentMethod;
import com.camhr.employer.wallet.constants.RechargeStatus;
import com.camhr.employer.wallet.entity.Recharge;
import com.camhr.employer.wallet.mapper.RechargeMapper;
import java.math.BigDecimal;
import java.util.Date;
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
public class RechargeMapperTest {

  @Autowired
  private RechargeMapper rechargeMapper;

  private Recharge recharge = new Recharge();

  @Test
  public void addRechargeMapper() {
    recharge.setType(PaymentMethod.BANK.value());
    recharge.setStatus(RechargeStatus.NOPAY.value());
    recharge.setCdate(new Date());
    recharge.setCash(new BigDecimal("2292.34"));
    recharge.setCashrep(new BigDecimal("2292.34"));
    recharge.setEmployerId(4848484);
    recharge.setDescription("hhl");
    recharge.setPayTime(new Date());
    int rows = rechargeMapper.addRecharge(recharge);
    System.out.println("ID: " + recharge.getId());
    Assert.assertEquals(1, rows);
    Assert.assertTrue(recharge.getId() > 0);
  }
}
