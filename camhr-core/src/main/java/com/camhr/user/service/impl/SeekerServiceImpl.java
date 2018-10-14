package com.camhr.user.service.impl;

import com.camhr.constants.AccountConstants;
import com.camhr.constants.AccountType;
import com.camhr.constants.Language;
import com.camhr.im.entity.IMAccount;
import com.camhr.im.service.IMService;
import com.camhr.resume.entity.Seeker;
import com.camhr.user.entity.SeekerMobileRegParam;
import com.camhr.user.entity.SeekerRegParam;
import com.camhr.user.error.UserErrorCodes;
import com.camhr.user.mapper.SeekerMapper;
import com.camhr.user.service.SeekerService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import we.lang.Exceptions;
import we.lang.IdWorker;
import we.lang.Q;
import we.security.authentication.jwt.JwtAccessTokenConverter;
import we.security.authentication.jwt.JwtToken;
import we.security.passport.Account;
import we.security.passport.Passport;
import we.security.passport.PassportModule;
import we.security.passport.PassportModule.ErrorCodes;
import we.security.rbac.User;

@Service
public class SeekerServiceImpl implements SeekerService {

  @Autowired
  private SeekerMapper seekerMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IMService imService;

  private IdWorker idWorker = new IdWorker(1, 1, 1);

  @Override
  public int updateSeeker(Seeker seeker) {
    return seekerMapper.updateSeeker(seeker);
  }

  @Override
  public Seeker getSeekerById(long seekerId) {
    return seekerMapper.getSeekerById(seekerId);
  }

  @Override
  public int updateWorkStatus(int status, long seekerId) {
    return seekerMapper.updateWorkStatus(status, seekerId);
  }

  @Override
  public int registerSeeker(SeekerRegParam seekerRegParam) {
    if (seekerMapper.existUsername(seekerRegParam.getEmail())) {
      Exceptions.of(SeekerService.class).error(UserErrorCodes.USERNAME_EXIST);
    }
    if (seekerMapper.existEmail(seekerRegParam.getEmail())) {
      Exceptions.of(SeekerService.class).error(UserErrorCodes.EMAIL_EXIST);
    }
    Seeker seeker = new Seeker();
    seeker.setEmail(seekerRegParam.getEmail());
    seeker.setName(seekerRegParam.getEmail());
    seeker.setMobile("");
    seeker.setRegionCode("");
    seeker.setPassword(passwordEncoder.encode(seekerRegParam.getPassword()));
    seeker.setLanguage(seekerRegParam.getLanguage());
    return seekerMapper.addSeeker(seeker);
  }

  @Override
  public int registerSeekerByMobile(SeekerMobileRegParam mobileRegParam) {
    if (seekerMapper.existMobile(mobileRegParam.getRegionCode(), mobileRegParam.getMobile())) {
      Exceptions.of(SeekerService.class).error(UserErrorCodes.MOBILE_EXISTED);
    }
    //todo 校验短信验证码
    Seeker seeker = new Seeker();
    seeker.setMobile(mobileRegParam.getMobile());
    seeker.setRegionCode(mobileRegParam.getRegionCode());
    seeker.setLanguage(mobileRegParam.getLanguage());
    seeker.setName(seeker.getMobile());
    seeker.setPassword(passwordEncoder.encode(mobileRegParam.getPassword()));
    seeker.setEmail("");
    return seekerMapper.addSeeker(seeker);
  }

  @Override
  public int setDefaultLanguage(String language, long userId) {
    return seekerMapper.setDefaultLanguage(language, userId);
  }

  @Override
  public boolean isMobileExist(String regionCode, String mobile) {
    return seekerMapper.existMobile(regionCode, mobile);
  }

  @Override
  public boolean isEmailExist(String email) {
    return seekerMapper.existEmail(email);
  }

  @Override
  public boolean updatePasswordByPhoneOrEmail(String password, String regionCode, String phone, String email) {
    if (!StringUtils.isEmpty(phone)) {
      return seekerMapper.updatePasswordByPhone(password, regionCode, phone) > 0;
    } else if (!StringUtils.isEmpty(email)) {
      return seekerMapper.updatePasswordByEmail(password, email) > 0;
    }
    return false;
  }

  public boolean isExistedPassportName(String name) {

    return false;
  }

  @Override
  public Passport addPassport(Passport passport) {
    return new Passport();
  }

  @Override
  public Passport getPassport(String username) {
    Passport pass = seekerMapper.getUserPassportByName(username);
    if (pass == null) {
      Exceptions.of(PassportModule.NAME).error(ErrorCodes.UNKNOWN_ACCOUNT_EXCEPTION);
    }
    pass.setType(AccountType.SEEKER.value());
    relateIMAccount(pass);
    return pass;
  }

  private void relateIMAccount(Passport pass) {
    IMAccount imAccount = imService.getIMAccount(pass.getName(), AccountType.SEEKER.value());
    if (imAccount == null) {
      imAccount = new IMAccount();
      imAccount.setAccount(pass.getName());
      imAccount.setNickname(pass.getName());
      imAccount.setType(AccountType.SEEKER.value());
      imAccount.setAccessId(AccountConstants.SEEKER_PREFIX + pass.getUserId());
      imAccount.setToken(passwordEncoder.encode(String.valueOf(idWorker.nextId())));
      imAccount.setCreateAt(Q.now());
      imService.createIMAccount(imAccount);
    }
    pass.getAttributes().put("accid", imAccount.getAccessId());
    pass.getAttributes().put("imToken", imAccount.getToken());
  }

  @Override
  public int restPassport(long userId, String name, String password, String signature) {
    return 0;
  }

  @Override
  public int changePassportPassword(long userId, String oldPassword, String newPassword) {
    int result = seekerMapper.updatePassword(userId, passwordEncoder.encode(oldPassword),
        passwordEncoder.encode(newPassword));
    if (result == 0) {
      Exceptions.of(PassportModule.NAME).error(ErrorCodes.INCORRECT_OLD_CREDENTIALS_EXCEPTION);
    }
    return result;
  }

  @Override
  public int createRecoveryCode(String name, String code) {
    return 0;
  }

  @Override
  public int recoverPassport(String code, String name, String password) {
    return 0;
  }


  @Autowired(required = false)
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Override
  public Object getTokenByPassport(Passport passport) {
    JwtToken jwtToken = new JwtToken();
    User user = new User();
    user.setUserId(passport.getUserId());
    user.setGroup(AccountType.SEEKER.value());
    user.setRoles(Lists.newArrayList("USER"));
    user.setName(passport.getName());
    user.putAll(passport.getAttributes());
    jwtToken.accessToken(jwtAccessTokenConverter.encode(user));
    if (passport.isFirst()) {
      jwtToken.put("first", passport.isFirst());
    }
    return jwtToken;
  }

  @Override
  public Passport getConnectPassport(Account account) {
    return null;
  }
}
