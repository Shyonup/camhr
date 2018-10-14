package com.camhr.user.mapper;

import com.camhr.resume.entity.Skill;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SkillMapper {

  int updateSkill(Skill skill);

  int addSkill(Skill skill);

  int deleteSkill(long id);

  List<Skill> getSkillsByResumeId(long resumeId);
}
