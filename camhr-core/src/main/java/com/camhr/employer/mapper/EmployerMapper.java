package com.camhr.employer.mapper;

import com.camhr.employer.entity.Employer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import we.security.passport.Passport;

@Mapper
public interface EmployerMapper {

  Employer getEmployer(@Param("employerId") long employerId);

  int updateEmployerInfo(Employer employer);

  Passport getEmployerPassportByName(@Param("name") String name);

  int addEmployer(Employer employer);

  int getNextSequence();

  boolean existUsername(@Param("username") String name);

  int setDefaultLanguage(@Param("language") String language, @Param("employerId") long employerId);

  boolean existEmail(@Param("email") String email);

  boolean existMobile(@Param("regionCode") String regionCode, @Param("mobile") String mobile);

  boolean isFeature(@Param("employerId") long employerId);

  int updatePassword(@Param("employerId") long employerId, @Param("oldPassword") String oldPassword,
      @Param("newPassword") String newPassword);

  int updatePasswordByPhone(@Param("password") String password, @Param("region") String region,
      @Param("phone") String phone);

  int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);
}
