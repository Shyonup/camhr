package com.camhr.user.mapper;

import com.camhr.resume.entity.CareerProfile;
import com.camhr.resume.entity.Resume;
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
public class UserResumeMapperTest {

  @Autowired
  private UserResumeMapper userResumeMapper;

  @Test
  public void updateCareerProfile() {
    Resume resume = resume();
    userResumeMapper.addUserResume(resume);
    CareerProfile careerProfile = careerProfile();
    careerProfile.setResumeId(resume.getResumeId());
    int row = userResumeMapper.updateCareerProfile(careerProfile);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void delete() {
    Resume resume = resume();
    userResumeMapper.addUserResume(resume);
    int row = userResumeMapper.deleteResumeById(resume.getResumeId());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void addUserResume() {
    int row = userResumeMapper.addUserResume(resume());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void getResumeById() {
    Resume resume = resume();
    userResumeMapper.addUserResume(resume);
    Resume resumeFromDb = userResumeMapper.getResumeById(resume.getResumeId());
    Assert.assertTrue(resumeFromDb != null);
  }

  @Test
  public void update() {
    Resume resume = resume();
    userResumeMapper.addUserResume(resume);
    resume.setReqJobTitle("期望职位");
    resume.setReqJobtermId(1);
    int row = userResumeMapper.updateJobIntention(resume);
    Assert.assertEquals(row, 1);
  }

  public CareerProfile careerProfile() {
    CareerProfile careerProfile = new CareerProfile();
    careerProfile.setResumeId(25835);
    careerProfile.setCareerCategory(1);
    careerProfile.setCareerExperience(2);
    careerProfile.setCareerIndustry(3);
    careerProfile.setCareerLevel(3);
    careerProfile.setCareerPosition("3");
    careerProfile.setQualificationId(2);
    careerProfile.setSalary(2);
    return careerProfile;
  }

  public Resume resume() {
    Resume resume = new Resume();
    resume.setCreateTime(new Date());
    resume.setName("简历名称");
    resume.setSeekerId(2);
    return resume;
  }
}
