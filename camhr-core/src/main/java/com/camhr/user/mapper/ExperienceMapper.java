package com.camhr.user.mapper;

import com.camhr.resume.entity.Experience;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExperienceMapper {

  int addExperience(Experience experience);

  int updateExperience(Experience experience);

  List<Experience> getExperiencesByResumeId(long resumeId);

  int  deleteExperience(long experienceId);
}
