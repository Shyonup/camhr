package com.camhr.employer.service;

import com.camhr.employer.entity.Employer;
import com.camhr.employer.entity.EmployerRegParam;
import java.io.IOException;
import we.oss.PutObjectResult;
import we.security.passport.PassportService;

public interface EmployerService extends PassportService {

  /**
   * 返回一下公司的基本信息
   */
  Employer getEmployerBaseInfo(long employerId);

  /**
   * 返回去掉敏感信息后的公司信息
   */
  Employer getEmployerWithoutSensitiveInfo(long employerId);

  // TODO 缓存注解
  Employer getEmployer(long employerId);

  int updateEmployerInfo(Employer employer);

  PutObjectResult uploadEmployerLogo(String originalFilename, byte[] data)
      throws IOException;

  PutObjectResult uploadEmployerImage(String originalFilename, byte[] data)
      throws IOException;

  int registerEmployer(EmployerRegParam employerRegParam);

  int setDefaultLanguage(String language, long userId);

  /**
   * 判断企业账号是否具有免费发布职位的权限
   */
  boolean isPublishJobFree(long employerId);

  boolean isMobileExist(String regionCode, String mobile);

  boolean isEmailExist(String email);

  boolean updatePasswordByPhoneOrEmail(String password, String regionCode, String phone,
      String email);
}
