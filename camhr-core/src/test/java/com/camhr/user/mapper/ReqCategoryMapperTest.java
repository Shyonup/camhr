package com.camhr.user.mapper;

import com.camhr.resume.entity.Intention;
import com.camhr.resume.entity.ReqCategory;
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
public class ReqCategoryMapperTest {

  @Autowired
  private ReqCategoryMapper reqCategoryMapper;

  @Test
  public void addCategory() {
    Intention intention = intention();
    List<Integer> list = intention.getCategoryIds();
    List<ReqCategory> reqCategoryList = Lists.newArrayList();
    for (Integer categoryId : list) {
      ReqCategory reqCategory = new ReqCategory();
      reqCategory.setResumeId(intention.getResumeId());
      reqCategory.setCategoryId(categoryId);
      reqCategoryList.add(reqCategory);
    }
    long row = reqCategoryMapper.addCategory(reqCategoryList);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteCategoryByResumeId() {
    Intention intention = intention();
    addCategory();
    long row = reqCategoryMapper.deleteCategoryByResumeId(intention.getResumeId());
  }

  @Test
  public void getReqCategoryIdsByResumeId() {
    Intention intention = intention();
    addCategory();
    List<Integer> list = reqCategoryMapper.getReqCategoryIdsByResumeId(intention.getResumeId());
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
