package com.camhr.user.mapper;

import com.camhr.resume.entity.Qualification;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QualificationMapper {

  int deleteQualification(long id);

  int addQualification(Qualification qualification);

  int updateQualification(Qualification qualifications);

  List<Qualification> getQualificationByResumeId(long resumeId);
}
