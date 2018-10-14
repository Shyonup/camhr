package com.camhr.user.mapper;

import com.camhr.resume.entity.Intention;
import com.camhr.resume.entity.ReqLocation;
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
public class ReqLocationMapperTest {

  @Autowired
  private ReqLocationMapper reqLocationMapper;

  @Test
  public void addReqLocation() {
    Intention intention = intention();
    List<Integer> list = intention.getCategoryIds();
    List<ReqLocation> locationList = Lists.newArrayList();
    for (Integer locationId : list) {
      ReqLocation reqLocation = new ReqLocation();
      reqLocation.setResumeId(intention.getResumeId());
      reqLocation.setLocationId(locationId);
      locationList.add(reqLocation);
    }
    long row = reqLocationMapper.addReqLocation(locationList);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteCategoryByResumeId() {
    Intention intention = intention();
    addReqLocation();
    long row = reqLocationMapper.deleteReqLocationByResumeId(intention.getResumeId());
  }

  @Test
  public void get() {
    Intention intention = intention();
    addReqLocation();
    List<Integer> list = reqLocationMapper.getReqLocationIds(intention.getResumeId());
    Assert.assertTrue(list.size() > 0);
  }

  public Intention intention() {
    Intention intention = new Intention();
    intention.setCategoryIds(Lists.newArrayList(1));
    intention.setResumeId(1);
    intention.setIndustryIds(Lists.newArrayList(1));
    intention.setJobTerm(1);
    intention.setLocationIds(Lists.newArrayList(1));
    intention.setCategoryIds(Lists.newArrayList(1));
    intention.setSalary(1);
    intention.setWorkStatus(1);
    return intention;
  }
}
