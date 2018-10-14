package com.camhr.user.mapper;

import com.camhr.resume.entity.Education;
import java.util.Date;
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
public class EducationMapperTest {

  @Autowired
  private EducationMapper educationMapper;

  @Test
  public void addEducation() {
    long row = educationMapper.addEducation(education());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteEducation() {
    Education education = education();
    educationMapper.addEducation(education);
    long row = educationMapper.deleteEducation(education.getEduId());
    Assert.assertEquals(1, row);
  }


  @Test
  public void updateEducation() {
    Education education = education();
    education.setName("2222");
    educationMapper.addEducation(education);
    long row = educationMapper.updateEducation(education());
  }

  @Test
  public void getEducationsByResumeId() {
    Education education = education();
    educationMapper.addEducation(education);
    List<Education> list = educationMapper.getEducationsByResumeId(education.getResumeId());
    Assert.assertTrue(list.size() > 0);
  }

  public Education education() {
    Education education = new Education();
    education.setResumeId(28566);
    education.setFromDate(new Date());
    education.setMajor("自动化");
    education.setName("中山大学");
    education.setQualificationId(4);
    education.setToDate(new Date());
    education.setDescription("描述");
    return education;
  }
}
