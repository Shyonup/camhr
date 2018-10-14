package com.camhr.user.service.impl;

import com.camhr.resume.entity.Qualification;
import com.camhr.user.mapper.QualificationMapper;
import com.camhr.user.service.QualificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QualificationServiceImpl implements QualificationService {

  @Autowired
  private QualificationMapper qualificationMapper;

  @Override
  public int updateQualification(Qualification qualification) {
    return qualificationMapper.updateQualification(qualification);
  }

  @Override
  public int addQualification(Qualification qualification) {
    return qualificationMapper.addQualification(qualification);
  }

  @Override
  public int deleteQualification(long id) {
    return qualificationMapper.deleteQualification(id);
  }

  @Override
  public List<Qualification> getQualificationsByResumeId(long resumeId) {
    return qualificationMapper.getQualificationByResumeId(resumeId);
  }
}
