package com.camhr.user.mapper;


import com.camhr.resume.entity.Experience;
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
public class ExperienceMapperTest {

  @Autowired
  private ExperienceMapper experienceMapper;

  @Test
  public void addExperience() {
    long row = experienceMapper.addExperience(experience());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void updateExperience() {
    Experience experience = experience();
    experienceMapper.addExperience(experience);
    experience.setTitle("1111");
    experienceMapper.updateExperience(experience);
  }

  @Test
  public void getExperiencesByResumeId() {
    Experience experience = experience();
    experienceMapper.addExperience(experience);
    List<Experience> list = experienceMapper.getExperiencesByResumeId(experience.getResumeId());
    Assert.assertTrue(list.size() > 0);

  }

  @Test
  public void deleteExperience() {
    Experience experience = experience();
    experienceMapper.addExperience(experience);
    experienceMapper.deleteExperience(experience.getExperienceId());
  }

  public Experience experience() {
    Experience experience = new Experience();
    experience.setCompany("佳都商务");
    experience.setResumeId(1);
    experience.setDescription("工作经历");
    experience.setExperienceId(1);
    experience.setFromDate(new Date());
    experience.setTitle("销售");
    experience.setToDate(new Date());
    return experience;
  }

}
