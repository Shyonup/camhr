package com.camhr.user.service.impl;

import com.camhr.resume.entity.Education;
import com.camhr.user.mapper.EducationMapper;
import com.camhr.user.service.EducationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationServiceImpl implements EducationService {

  @Autowired
  private EducationMapper educationMapper;

  @Override
  public int addEducation(Education education) {
    return educationMapper.addEducation(education);
  }

  @Override
  public int updateEducation(Education education) {
    return educationMapper.updateEducation(education);
  }

  @Override
  public List<Education> getEducationsByResumeId(long resumeId) {
    return educationMapper.getEducationsByResumeId(resumeId);
  }


  @Override
  public int deleteEducation(long eduId) {
    return educationMapper.deleteEducation(eduId);
  }
}
