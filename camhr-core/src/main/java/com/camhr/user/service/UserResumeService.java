package com.camhr.user.service;


import com.camhr.resume.entity.CareerProfile;
import com.camhr.resume.entity.Resume;
import java.io.IOException;
import java.util.List;
import we.oss.PutObjectResult;

public interface UserResumeService {

  long addUserResume(Resume resume);

  List<Resume> getResumesBySeekerId(long seekerId);

  Resume getResumeBaseInfo(long resumeId);

  Resume getResumeByResumeId(long resumeId);

  int updateResumeAttributes(Resume resume);

  int deleteResumeById(long resumeId);

  int updateCareerProfile(CareerProfile careerProfile);

  int updateJobIntention(Resume resume);

  PutObjectResult uploadImages(String originalFilename, byte[] data)
      throws IOException;
}
