package com.camhr.user.service;

import com.camhr.resume.entity.Seeker;
import com.camhr.user.entity.SeekerMobileRegParam;
import com.camhr.user.entity.SeekerRegParam;
import we.security.passport.PassportService;

public interface SeekerService extends PassportService {

  int updateSeeker(Seeker seeker);

  Seeker getSeekerById(long seekerId);

  int updateWorkStatus(int status, long seekerId);

  int registerSeeker(SeekerRegParam seekerRegParam);

  int registerSeekerByMobile(SeekerMobileRegParam mobileRegParam);

  int setDefaultLanguage(String language, long userId);

  boolean isMobileExist(String regionCode, String mobile);

  boolean isEmailExist(String email);

  boolean updatePasswordByPhoneOrEmail(String password, String regionCode, String phone, String email);
}
