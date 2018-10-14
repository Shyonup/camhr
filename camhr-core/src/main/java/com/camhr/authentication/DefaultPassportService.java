package com.camhr.authentication;

import com.camhr.constants.AccountType;
import com.camhr.employer.service.EmployerService;
import com.camhr.user.service.SeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import we.lang.Numbers;
import we.security.passport.Account;
import we.security.passport.Passport;
import we.security.passport.PassportService;

@Service
@Primary
public class DefaultPassportService implements PassportService {

  @Autowired
  private SeekerService seekerService;

  @Autowired
  private EmployerService employerService;

  private PassportService getPassportService(int type) {
    if (type == AccountType.SEEKER.value()) {
      return seekerService;
    }
    if (type == AccountType.EMPLOYER.value()) {
      return employerService;
    }
    return seekerService;
  }

  private PassportService getPassportService(Passport passport) {
    return getPassportService(passport.getType());
  }


  @Override
  public Passport getPassport(String username) {
    int type = Numbers.toInteger(username.substring(0, 1));
    return getPassportService(type).getPassport(username.substring(1));
  }


  @Override
  public Object getTokenByPassport(Passport passport) {
    return getPassportService(passport).getTokenByPassport(passport);
  }

  @Override
  public Passport getConnectPassport(Account account) {
    return null;
  }


  /*============================Current version not implements these methods=========================================*/
  @Override
  public int restPassport(long userId, String name, String password, String signature) {
    //暂时不处理
    return 0;
  }

  @Override
  public int changePassportPassword(long userId, String oldPassword, String newPassword) {
    //暂时不处理
    return 0;
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
  public int createRecoveryCode(String name, String code) {
    return 0;
  }

  @Override
  public int recoverPassport(String code, String name, String password) {
    return 0;
  }
}
