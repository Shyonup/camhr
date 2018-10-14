package com.camhr.user.service.impl;

import com.camhr.resume.entity.Experience;
import com.camhr.user.mapper.ExperienceMapper;
import com.camhr.user.service.ExperienceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl implements ExperienceService {

  @Autowired
  private ExperienceMapper experienceMapper;

  @Override
  public int addExperience(Experience experience) {
    return experienceMapper.addExperience(experience);
  }

  @Override
  public int updateExperience(Experience experience) {
    return experienceMapper.updateExperience(experience);
  }

  @Override
  public int deleteExperience(long experienceId) {
    return experienceMapper.deleteExperience(experienceId);
  }

  @Override
  public List<Experience> getExperiencesByResumeId(long resumeId) {
    return experienceMapper.getExperiencesByResumeId(resumeId);
  }
}
