package com.camhr.user.service.impl;

import com.camhr.resume.entity.CareerProfile;
import com.camhr.resume.entity.Education;
import com.camhr.resume.entity.Experience;
import com.camhr.resume.entity.Intention;
import com.camhr.resume.entity.LanguageLevel;
import com.camhr.resume.entity.Qualification;
import com.camhr.resume.entity.Resume;
import com.camhr.resume.entity.Seeker;
import com.camhr.resume.entity.Skill;
import com.camhr.user.config.UserResumeProperties;
import com.camhr.user.error.ResumeErrorCodes;
import com.camhr.user.mapper.UserResumeMapper;
import com.camhr.user.service.EducationService;
import com.camhr.user.service.ExperienceService;
import com.camhr.user.service.IntentionService;
import com.camhr.user.service.LanguageLevelService;
import com.camhr.user.service.QualificationService;
import com.camhr.user.service.SeekerService;
import com.camhr.user.service.SkillService;
import com.camhr.user.service.UserResumeService;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import we.lang.Exceptions;
import we.oss.ObjectKey;
import we.oss.PutObjectRequest;
import we.oss.PutObjectResult;
import we.oss.StorageService;

@Service
public class UserResumeServiceImpl implements UserResumeService {

  @Autowired
  private UserResumeMapper userResumeMapper;

  @Autowired
  private EducationService educationService;

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private IntentionService intentionService;

  @Autowired
  private SeekerService seekerService;

  @Autowired
  private SkillService skillService;

  @Autowired
  private QualificationService qualificationService;

  @Autowired
  private LanguageLevelService languageLevelService;

  @Autowired
  private StorageService storageService;

  public static final String RESUME_NAME = "Resume";

  /**
   * 新增简历
   */
  @Override
  public long addUserResume(Resume resume) {
    resume.setCreateTime(new Date());
    List<Resume> list = userResumeMapper.findResumesBySeekerId(resume.getSeekerId());
    if (list.size() >= UserResumeProperties.RESUME_NUM_MAX) {
      Exceptions.of(UserResumeService.class).error(ResumeErrorCodes.QUANTITY_OVER_LIMIT);
    }
    resume.setName(RESUME_NAME + (1 + list.size()));
    userResumeMapper.addUserResume(resume);
    return resume.getResumeId();
  }

  /**
   * 获取简历列表
   */
  @Override
  public List<Resume> getResumesBySeekerId(long seekerId) {
    List<Resume> resumes = userResumeMapper.findResumesBySeekerId(seekerId);
    //获取个人信息
    Seeker seeker = new Seeker();
    if (!CollectionUtils.isEmpty(resumes)) {
      seeker = seekerService.getSeekerById(resumes.get(0).getSeekerId());
    }
    for (Resume resume : resumes) {
      //设置个人信息
      resume.setProfile(seeker);
      //获取教育经历
      List<Education> educationList = educationService
          .getEducationsByResumeId(resume.getResumeId());
      resume.setEducations(educationList);
      //获取工作经历
      List<Experience> experienceList = experienceService
          .getExperiencesByResumeId(resume.getResumeId());
      resume.setExperiences(experienceList);
      //获取求职意向
      Intention intention = intentionService.getIntention(resume.getResumeId());
      intention.setReqJobTitle(resume.getReqJobTitle());
      intention.setJobTerm(resume.getReqJobtermId());
      intention.setWorkStatus(seeker.getWorkStatus());
      resume.setIntention(intention);
      //获取技能
      List<Skill> skillList = skillService.getSkillsByResumeId(resume.getResumeId());
      resume.setSkills(skillList);
      //获取证书
      List<Qualification> qualificationList = qualificationService
          .getQualificationsByResumeId(resume.getResumeId());
      resume.setQualifications(qualificationList);
      //获取语言水平
      List<LanguageLevel> languageLevelList = languageLevelService
          .getLanguageLevelsByResumeId(resume.getResumeId());
      resume.setLanguageLevels(languageLevelList);
    }
    return resumes;
  }

  @Override
  public Resume getResumeBaseInfo(long resumeId) {
    return userResumeMapper.getResumeById(resumeId);
  }


  @Override
  public Resume getResumeByResumeId(long resumeId) {
    Resume resume = getResumeBaseInfo(resumeId);
    if (resume == null) {
      Exceptions.of(UserResumeService.class).notFound(ResumeErrorCodes.RESUME_NOT_EXISTS);
    }
    //获取个人信息
    Seeker seeker = seekerService.getSeekerById(resume.getSeekerId());
    resume.setProfile(seeker);
    //获取教育经历
    List<Education> educationList = educationService.getEducationsByResumeId(resumeId);
    resume.setEducations(educationList);
    //获取工作经历
    List<Experience> experienceList = experienceService
        .getExperiencesByResumeId(resumeId);
    resume.setExperiences(experienceList);
    //获取求职意向
    Intention intention = intentionService.getIntention(resumeId);
    intention.setReqJobTitle(resume.getReqJobTitle());
    intention.setJobTerm(resume.getReqJobtermId());
    intention.setWorkStatus(seeker.getWorkStatus());
    resume.setIntention(intention);
    //获取技能
    List<Skill> skillList = skillService.getSkillsByResumeId(resumeId);
    resume.setSkills(skillList);
    //获取证书
    List<Qualification> qualificationList = qualificationService
        .getQualificationsByResumeId(resumeId);
    resume.setQualifications(qualificationList);
    //获取语言水平
    List<LanguageLevel> languageLevelList = languageLevelService
        .getLanguageLevelsByResumeId(resumeId);
    resume.setLanguageLevels(languageLevelList);
    return resume;
  }

  /**
   * 更新兴趣爱好，培训经历，自我评价
   */
  @Override
  public int updateResumeAttributes(Resume resume) {
    resume.setUpdateTime(new Date());
    return userResumeMapper.updateResumeAttributes(resume);
  }

  @Override
  public int deleteResumeById(long resumeId) {
    return userResumeMapper.deleteResumeById(resumeId);
  }

  @Override
  public int updateCareerProfile(CareerProfile careerProfile) {
    careerProfile.setUpdateTime(new Date());
    return userResumeMapper.updateCareerProfile(careerProfile);
  }

  /**
   * 处理求职意向中的工作性质与期望职位两个字段
   */
  @Override
  public int updateJobIntention(Resume resume) {
    return userResumeMapper.updateJobIntention(resume);
  }

  @Override
  public PutObjectResult uploadImages(String originalFilename, byte[] data) throws IOException {
    return storageService
        .upload(
            PutObjectRequest
                .of("users", ObjectKey.of("resumes", originalFilename), data)
                .accept(MediaType.ANY_IMAGE_TYPE));
  }
}
