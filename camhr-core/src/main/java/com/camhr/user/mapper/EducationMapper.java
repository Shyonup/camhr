package com.camhr.user.mapper;

import com.camhr.resume.entity.Education;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EducationMapper {

  int addEducation(Education education);

  int updateEducation(Education education);

  List<Education> getEducationsByResumeId(long resumeId);

  int  deleteEducation(long eduId);
}
