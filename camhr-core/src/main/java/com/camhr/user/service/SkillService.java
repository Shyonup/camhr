package com.camhr.user.service;

import com.camhr.resume.entity.Skill;
import java.util.List;

public interface SkillService {

  int updateSkill(Skill skill);

  int addSkill(Skill skill);

  int deleteSkill(long id);

  List<Skill> getSkillsByResumeId(long resumeId);
}
