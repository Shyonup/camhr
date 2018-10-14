package com.camhr.user.mapper;

import com.camhr.resume.entity.CareerProfile;
import com.camhr.resume.entity.Resume;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserResumeMapper {

  int addUserResume(Resume resume);

  List<Resume> findResumesBySeekerId(long seekerId);

  int updateResumeAttributes(Resume resume);

  int deleteResumeById(long resumeId);

  int updateCareerProfile(CareerProfile careerProfile);

  Resume getResumeById(long resumeId);

  int updateJobIntention(Resume resume);
}
