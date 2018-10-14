package com.camhr.user.service.impl;

import com.camhr.resume.entity.Skill;
import com.camhr.user.mapper.SkillMapper;
import com.camhr.user.service.SkillService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class SkillServiceImpl implements SkillService {

  @Autowired
  private SkillMapper skillMapper;

  @Override
  public int updateSkill(Skill skill) {
    return skillMapper.updateSkill(skill);
  }

  @Override
  public int addSkill(Skill skill) {
    return skillMapper.addSkill(skill);
  }

  @Override
  public int deleteSkill(long id) {
    return skillMapper.deleteSkill(id);
  }

  @Override
  public List<Skill> getSkillsByResumeId(long resumeId) {
    return skillMapper.getSkillsByResumeId(resumeId);
  }
}
