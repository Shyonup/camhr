package com.camhr.user.service;

import com.camhr.resume.entity.Qualification;
import java.util.List;

public interface QualificationService {

  int updateQualification(Qualification qualification);

  int addQualification(Qualification qualification);

  int deleteQualification(long id);

  List<Qualification> getQualificationsByResumeId(long resumeId);
}
