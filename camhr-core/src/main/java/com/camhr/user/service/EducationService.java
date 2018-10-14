package com.camhr.user.service;

import com.camhr.resume.entity.Education;
import java.util.List;

public interface EducationService {

  int addEducation(Education education);

  int updateEducation(Education education);

  List<Education> getEducationsByResumeId(long resumeId);

  int deleteEducation(long eduId);
}
