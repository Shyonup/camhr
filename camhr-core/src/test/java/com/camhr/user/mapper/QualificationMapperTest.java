package com.camhr.user.mapper;

import com.camhr.resume.entity.Qualification;
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
public class QualificationMapperTest {

  @Autowired
  private QualificationMapper qualificationMapper;

  @Test
  public void addQualification() {
    int row = qualificationMapper.addQualification(qualification());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteQualification() {
    Qualification qualification = qualification();
    qualificationMapper.addQualification(qualification);
    int row = qualificationMapper.deleteQualification(qualification.getId());
    Assert.assertEquals(1, row);
  }


  @Test
  public void updateQualification() {
    Qualification qualification = qualification();
    qualificationMapper.addQualification(qualification);
    qualification.setName("名称222");
    int row = qualificationMapper.updateQualification(qualification);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void getEducationsByResumeId() {
    Qualification qualification = qualification();
    qualificationMapper.addQualification(qualification);
    List<Qualification> list = qualificationMapper
        .getQualificationByResumeId(qualification.getResumeId());
    Assert.assertTrue(list.size() > 0);
  }

  public Qualification qualification() {
    Qualification qualification = new Qualification();
    qualification.setId(1);
    qualification.setResumeId(3);
    qualification.setIssued("机构");
    qualification.setName("名称");
    qualification.setObtained(1);
    qualification.setPhoto("url");
    return qualification;
  }
}
