package com.camhr.user.mapper;

import com.camhr.resume.entity.Seeker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import we.security.passport.Passport;

@Mapper
public interface SeekerMapper {

  int updateSeeker(Seeker seeker);

  Seeker getSeekerById(long seekerId);

  int updateWorkStatus(@Param("workStatus") int workStatus, @Param("seekerId") long seekerId);

  /**
   * 账号状态，0--待审核，1--正常， 9--停用'
   *
   * @see we.security.passport.constants.PassportStatus
   */
  Passport getUserPassportByName(String username);

  int addSeeker(Seeker seeker);

  boolean existUsername(@Param("username") String username);

  boolean existEmail(@Param("email") String email);

  boolean existMobile(@Param("regionCode") String regionCode, @Param("mobile") String mobile);

  int setDefaultLanguage(@Param("language") String language, @Param("seekerId") long seekerId);

  int updatePassword(@Param("seekerId") long seekerId, @Param("oldPassword") String oldPassword,
      @Param("newPassword") String newPassword);

  int updatePasswordByPhone(@Param("password") String password, @Param("region") String region,
      @Param("phone") String phone);

  int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);
}

