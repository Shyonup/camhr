package com.camhr.employer.service.impl;

import com.camhr.constants.AccountConstants;
import com.camhr.constants.AccountType;
import com.camhr.constants.PositionConstants;
import com.camhr.employer.entity.Contact;
import com.camhr.employer.entity.Employer;
import com.camhr.employer.entity.EmployerRegParam;
import com.camhr.employer.mapper.EmployerMapper;
import com.camhr.employer.service.ContactService;
import com.camhr.employer.service.EmployerService;
import com.camhr.im.entity.IMAccount;
import com.camhr.im.service.IMService;
import com.camhr.job.config.JobConfiguration;
import com.camhr.user.error.UserErrorCodes;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import we.lang.Exceptions;
import we.lang.IdWorker;
import we.lang.Q;
import we.oss.ObjectKey;
import we.oss.PutObjectRequest;
import we.oss.PutObjectResult;
import we.oss.StorageService;
import we.security.authentication.jwt.JwtAccessTokenConverter;
import we.security.authentication.jwt.JwtToken;
import we.security.passport.Account;
import we.security.passport.Passport;
import we.security.passport.PassportModule;
import we.security.passport.PassportModule.ErrorCodes;
import we.security.rbac.User;

@Service("employerService")
public class EmployerServiceImpl implements EmployerService {

  @Autowired
  private EmployerMapper employerMapper;

  @Autowired
  private StorageService storageService;

  @Autowired
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  private ContactService contactService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IMService imService;

  private IdWorker idWorker = new IdWorker(1, 1, 1);

  @Override
  public Employer getEmployerBaseInfo(long employerId) {
    Employer employer = getEmployerWithoutSensitiveInfo(employerId);
    if (employer != null) {
      employer.setDescription(""); // 不返回公司的详细描述
    }
    return employer;
  }

  @Override
  public Employer getEmployer(long employerId) {
    return employerMapper.getEmployer(employerId);
  }

  @Override
  public int updateEmployerInfo(Employer employer) {
    return employerMapper.updateEmployerInfo(employer);
  }

  @Override
  public PutObjectResult uploadEmployerLogo(String originalFilename, byte[] data)
      throws IOException {
    return storageService
        .upload(
            PutObjectRequest
                .of("employers", ObjectKey.of("logo", originalFilename), data)
                .accept(MediaType.ANY_IMAGE_TYPE));
  }

  @Override
  public PutObjectResult uploadEmployerImage(String originalFilename, byte[] data)
      throws IOException {
    return storageService
        .upload(
            PutObjectRequest
                .of("employers", ObjectKey.of("image", originalFilename), data)
                .accept(MediaType.ANY_IMAGE_TYPE));
  }

  @Override
  public int registerEmployer(EmployerRegParam employerRegParam) {
    if (employerMapper.existUsername(employerRegParam.getName())) {
      Exceptions.of(EmployerService.class).error(UserErrorCodes.USERNAME_EXIST);
    }
    if (employerMapper.existEmail(employerRegParam.getEmail())) {
      Exceptions.of(EmployerService.class).error(UserErrorCodes.EMAIL_EXIST);
    }
    if (employerMapper
        .existMobile(employerRegParam.getRegionCode(), employerRegParam.getMobile())) {
      Exceptions.of(EmployerService.class).error(UserErrorCodes.MOBILE_EXISTED);
    }
    //todo 校验短信验证码
    int employerId = employerMapper.getNextSequence();
    Contact contact = new Contact();
    contact.setName(employerRegParam.getContactname());
    contact.setPositionId(employerRegParam.getPositionId());
    contact.setTelephone(employerRegParam.getRegionCode() + employerRegParam.getMobile());
    contact.setEmail(employerRegParam.getEmail());
    contact.setEmployerId(employerId);
    contactService.addContact(contact);
    Employer employer = new Employer();
    employer.setEmployerId(employerId);
    employer.setRegionCode(employerRegParam.getRegionCode());
    employer.setMobile(employerRegParam.getMobile());
    employer.setEmail(employerRegParam.getEmail());
    employer.setLocationId(employerRegParam.getLocationId());
    employer.setName(employerRegParam.getName());
    employer.setPassword(passwordEncoder.encode(employerRegParam.getPassword()));
    employer.setCompany(employerRegParam.getCompany());
    employer.setLanguage(employerRegParam.getLanguage());
    employer.setContactId(contact.getId());
    return employerMapper.addEmployer(employer);
  }

  @Override
  public int setDefaultLanguage(String language, long userId) {
    return employerMapper.setDefaultLanguage(language, userId);
  }

  @Override
  public boolean isPublishJobFree(long employerId) {
    if (!JobConfiguration.JOB_PUBLISH_PAY) {
      return true;
    }
    if (!JobConfiguration.JOB_FEATURED_PAY) {
      boolean isFeature = employerMapper.isFeature(employerId);
      if (isFeature) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isMobileExist(String regionCode, String mobile) {
    return employerMapper.existMobile(regionCode, mobile);
  }

  @Override
  public boolean isEmailExist(String email) {
    return employerMapper.existEmail(email);
  }

  @Override
  public boolean updatePasswordByPhoneOrEmail(String password, String regionCode, String phone,
      String email) {
    if (!StringUtils.isEmpty(phone)) {
      return employerMapper.updatePasswordByPhone(password, regionCode, phone) > 0;
    } else if (!StringUtils.isEmpty(email)) {
      return employerMapper.updatePasswordByEmail(password, email) > 0;
    }
    return false;
  }

  @Override
  public boolean isExistedPassportName(String name) {
    return false;
  }

  @Override
  public Passport addPassport(Passport passport) {
    return null;
  }

  @Override
  public Passport getPassport(String username) {
    Passport pass = employerMapper.getEmployerPassportByName(username);
    if (pass == null) {
      Exceptions.of(PassportModule.NAME).error(ErrorCodes.UNKNOWN_ACCOUNT_EXCEPTION);
    }
    pass.setType(AccountType.EMPLOYER.value());
    relateIMAccount(pass);
    return pass;
  }

  private void relateIMAccount(Passport pass) {
    IMAccount imAccount = imService.getIMAccount(pass.getName(), AccountType.EMPLOYER.value());
    if (imAccount == null) {
      imAccount = new IMAccount();
      imAccount.setAccount(pass.getName());
      imAccount.setNickname(pass.getName());
      imAccount.setType(AccountType.EMPLOYER.value());
      imAccount.setAccessId(AccountConstants.EMPLOYER_PREFIX + pass.getUserId());
      imAccount.setToken(passwordEncoder.encode(String.valueOf(idWorker.nextId())));
      imAccount.setCreateAt(Q.now());
      //添加im扩展信息
      int positionId = getPositionId(pass.getUserId());
      imAccount.getExtra().put("positionId", positionId);
      imAccount.getExtra().put("company", pass.getAttributes().get("company"));
      imService.createIMAccount(imAccount);
    }
    pass.getAttributes().put("accid", imAccount.getAccessId());
    pass.getAttributes().put("imToken", imAccount.getToken());
  }

  //旧系统一个企业账号有多个联系人，随机取一个联系人的职称给im
  private int getPositionId(long userId) {
    List<Contact> contacts = contactService.getContactsByEmployer(userId);
    if (contacts != null && !contacts.isEmpty()) {
      for (Contact contact : contacts) {
        if (contact.getPositionId() > 0) {
          return contact.getPositionId();
        }
      }
    }
    return PositionConstants.HR;
  }

  @Override
  public int restPassport(long userId, String name, String password, String signature) {
    return 0;
  }

  @Override
  public int changePassportPassword(long userId, String oldPassword, String newPassword) {
    int result = employerMapper.updatePassword(userId, passwordEncoder.encode(oldPassword),
        passwordEncoder.encode(newPassword));
    if (result == 0) {
      Exceptions.of(PassportModule.NAME).error(ErrorCodes.INCORRECT_OLD_CREDENTIALS_EXCEPTION);
    }
    return result;
  }

  @Override
  public Object getTokenByPassport(Passport passport) {
    JwtToken jwtToken = new JwtToken();
    User user = new User();
    user.setUserId(passport.getUserId());
    user.setGroup(AccountType.EMPLOYER.value());
    user.setRoles(Lists.newArrayList("ENTERPRISE"));
    user.setName(passport.getName());
    user.putAll(passport.getAttributes());
    jwtToken.accessToken(jwtAccessTokenConverter.encode(user));
    if (passport.isFirst()) {
      jwtToken.put("first", passport.isFirst());
    }
    return jwtToken;
  }

  @Override
  public Employer getEmployerWithoutSensitiveInfo(long employerId) {
    Employer employer = getEmployer(employerId);
    if (employer != null) {
      employer.setPassword(""); // 不返回公司的密码
    }
    return employer;
  }

  @Override
  public Passport getConnectPassport(Account account) {
    return null;
  }

  @Override
  public int createRecoveryCode(String name, String code) {
    return 0;
  }

  @Override
  public int recoverPassport(String code, String name, String password) {
    return 0;
  }
}
