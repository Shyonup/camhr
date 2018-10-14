package com.camhr.user.mapper;

import com.camhr.resume.entity.Intention;
import com.camhr.resume.entity.ReqIndustrial;
import com.google.common.collect.Lists;
import java.util.List;
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
public class ReqIndustrialMapperTest {

  @Autowired
  private ReqIndustrialMapper reqIndustrialMapper;

  @Test
  public void addIndustrial() {
    long row = reqIndustrialMapper.addIndustrial(reqIndustrial());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteIndustrialyByResumeId() {
    List<ReqIndustrial> reqIndustrial = reqIndustrial();
    reqIndustrialMapper.addIndustrial(reqIndustrial);
    long row = reqIndustrialMapper.deleteIndustrialByResumeId(reqIndustrial.get(0).getResumeId());
  }

  @Test
  public void get() {

    reqIndustrialMapper.addIndustrial(reqIndustrial());
    List<Integer> industrials = reqIndustrialMapper
        .getReqIndustrial(reqIndustrial().get(0).getResumeId());
    Assert.assertTrue(industrials.size() > 0);
  }

  public List<ReqIndustrial> reqIndustrial() {
    ReqIndustrial reqIndustrial = new ReqIndustrial();
    reqIndustrial.setResumeId(2);
    reqIndustrial.setIndustryId(2);
    reqIndustrial.setId(1);
    List<ReqIndustrial> list = Lists.newArrayList();
    list.add(reqIndustrial);
    return list;
  }
}
