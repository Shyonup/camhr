package com.camhr.user.mapper;

import com.camhr.resume.entity.Skill;
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
public class SkillMapperTest {

  @Autowired
  private SkillMapper skillMapper;

  @Test
  public void addSkill() {
    int row = skillMapper.addSkill(skill());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteSkill() {
    Skill skill = skill();
    skillMapper.addSkill(skill);
    int row = skillMapper.deleteSkill(skill.getId());
    Assert.assertEquals(1, row);
  }


  @Test
  public void updateSkill() {
    Skill skill = skill();
    skillMapper.addSkill(skill);
    skill.setName("名称222");
    int row = skillMapper.updateSkill(skill);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void getEducationsByResumeId() {
    Skill skill = skill();
    skillMapper.addSkill(skill);
    List<Skill> list = skillMapper
        .getSkillsByResumeId(skill.getResumeId());
    Assert.assertTrue(list.size() > 0);
  }

  public Skill skill() {
    Skill skill = new Skill();
    skill.setId(1);
    skill.setResumeId(2);
    skill.setName("机构");
    skill.setYears(2);
    return skill;
  }
}
