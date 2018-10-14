package com.camhr.user.service;

import com.camhr.resume.entity.Experience;
import java.util.List;

public interface ExperienceService {

  int addExperience(Experience experience);

  int updateExperience(Experience experience);

  List<Experience> getExperiencesByResumeId(long resumeId);

  int deleteExperience(long experienceId);
}
