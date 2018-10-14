package com.camhr.user.service.impl;

import com.camhr.resume.entity.Intention;
import com.camhr.resume.entity.ReqCategory;
import com.camhr.resume.entity.ReqIndustrial;
import com.camhr.resume.entity.ReqLocation;
import com.camhr.resume.entity.Resume;
import com.camhr.resume.entity.Seeker;
import com.camhr.user.mapper.ReqCategoryMapper;
import com.camhr.user.mapper.ReqIndustrialMapper;
import com.camhr.user.mapper.ReqLocationMapper;
import com.camhr.user.mapper.UserResumeMapper;
import com.camhr.user.service.IntentionService;
import com.camhr.user.service.SeekerService;
import com.camhr.user.service.UserResumeService;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IntentionServiceImpl implements IntentionService {

  @Autowired
  private ReqIndustrialMapper reqIndustrialMapper;

  @Autowired
  private ReqLocationMapper reqLocationMapper;

  @Autowired
  private ReqCategoryMapper reqCategoryMapper;

  @Autowired
  private UserResumeService userResumeService;

  @Autowired
  private SeekerService seekerService;

  @Autowired
  private UserResumeMapper userResumeMapper;

  @Override
  @Transactional
  public int updateIntension(Intention intention) {
    if (intention.getResumeId() == 0) {
      Resume resume = new Resume();
      resume.setSeekerId(intention.getSeekerId());
      long resumeId = userResumeService.addUserResume(resume);
      intention.setResumeId(resumeId);
    }
    reqCategoryMapper.deleteCategoryByResumeId(intention.getResumeId());
    reqIndustrialMapper.deleteIndustrialByResumeId(intention.getResumeId());
    reqLocationMapper.deleteReqLocationByResumeId(intention.getResumeId());
    List<Integer> categoryIdList = intention.getCategoryIds();
    //处理职位
    List<ReqCategory> categoryList = Lists.newArrayList();
    for (Integer categoryId : categoryIdList) {
      ReqCategory reqCategory = new ReqCategory();
      reqCategory.setResumeId(intention.getResumeId());
      reqCategory.setCategoryId(categoryId);
      categoryList.add(reqCategory);
    }
    //处理地点
    List<Integer> locationIdList = intention.getLocationIds();
    List<ReqLocation> locationList = Lists.newArrayList();
    for (Integer locationId : locationIdList) {
      ReqLocation reqLocation = new ReqLocation();
      reqLocation.setResumeId(intention.getResumeId());
      reqLocation.setLocationId(locationId);
      locationList.add(reqLocation);
    }
    //处理行业
    List<Integer> industryIds = intention.getIndustryIds();
    List<ReqIndustrial> reqIndustrial = Lists.newArrayList();
    for (Integer industryId : industryIds) {
      ReqIndustrial reqIndustrial1 = new ReqIndustrial();
      reqIndustrial1.setResumeId(intention.getResumeId());
      reqIndustrial1.setIndustryId(industryId);
      reqIndustrial.add(reqIndustrial1);
    }
    reqLocationMapper.addReqLocation(locationList);
    reqCategoryMapper.addCategory(categoryList);
    reqIndustrialMapper.addIndustrial(reqIndustrial);

    //处理期望职位、工作类型、期望薪水
    Resume resume = new Resume();
    resume.setResumeId(intention.getResumeId());
    resume.setReqJobtermId(intention.getJobTerm());
    resume.setReqJobTitle(intention.getReqJobTitle());
    resume.setReqSalaryId(intention.getSalary());
    userResumeService.updateJobIntention(resume);
    //处理工作状态
    seekerService.updateWorkStatus(intention.getWorkStatus(), intention.getSeekerId());
    return 1;
  }

  @Override
  public Intention getIntention(long resumeId) {
    List<Integer> locationList = reqLocationMapper.getReqLocationIds(resumeId);
    List<Integer> indutryIds = reqIndustrialMapper.getReqIndustrial(resumeId);
    List<Integer> reqCategoryList = reqCategoryMapper.getReqCategoryIdsByResumeId(resumeId);
    Intention intention = new Intention();
    intention.setResumeId(resumeId);
    intention.setCategoryIds(reqCategoryList);
    intention.setLocationIds(locationList);
    intention.setIndustryIds(indutryIds);
    return intention;
  }

  @Override
  public List<Intention> findIntentionsBySeekerId(long seekerId) {
    List<Resume> resumes = userResumeService.getResumesBySeekerId(seekerId);
    List<Intention> intentionList = Lists.newArrayList();
    for (Resume resume : resumes) {
      Intention intention = resume.getIntention();
      intention.setResumeId(resume.getResumeId());
      intention.setSalary(resume.getReqSalaryId());
      intention.setReqJobTitle(resume.getReqJobTitle());
      intention.setJobTerm(resume.getReqJobtermId());
      Seeker seeker = resume.getProfile();
      intention.setWorkStatus(seeker.getWorkStatus());
      intentionList.add(resume.getIntention());
    }
    return intentionList;
  }
}