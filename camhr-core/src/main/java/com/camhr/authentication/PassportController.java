package com.camhr.authentication;

import com.camhr.constants.AccountType;
import com.camhr.user.error.UserErrorCodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import we.lang.Exceptions;
import we.security.passport.Passport;
import we.security.passport.PassportModule;
import we.security.passport.PassportModule.ErrorCodes;
import we.security.passport.PassportService;
import we.web.Result;

public class PassportController {

  private Exceptions exceptions = Exceptions.of(PassportModule.NAME);

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PassportService passportService;


  public ResponseEntity authorize(String name, String password, AccountType accoutType) {
    try {
      Authentication authentication = authenticationManager
          .authenticate(
              new UsernamePasswordAuthenticationToken(accoutType.value() + name, password));
      Passport passport = (Passport) authentication.getPrincipal();
      return ResponseEntity.ok(passportService.getTokenByPassport(passport));
    } catch (AuthenticationException loginFailed) {
      SecurityContextHolder.clearContext();
      if (loginFailed instanceof LockedException) {
        exceptions.error(ErrorCodes.INCORRECT_CREDENTIALS_EXCEPTION);
      }

      if (loginFailed instanceof AccountStatusException) {
        exceptions.error(ErrorCodes.INCORRECT_CREDENTIALS_EXCEPTION);
      }
      return ResponseEntity.status(403).body(Result.of(0).fail(loginFailed.getMessage()));
    } catch (Exception e) {
      exceptions.error(ErrorCodes.INCORRECT_CREDENTIALS_EXCEPTION);
    }
    return null;
  }

  public ResponseEntity authorizeMobile(String mobile, String regionCode, AccountType accoutType) {
    try {
      Passport passport = passportService.getPassport(accoutType.value() + regionCode + mobile);
      if (passport == null) {
        exceptions.error(UserErrorCodes.MOBILE_NOT_EXIST);
      }
      //todo 校验短信验证码
      if (!StringUtils.equals("86", regionCode)) {
        exceptions.error(UserErrorCodes.INVALID_VERIFY_CODE);
      }
      return ResponseEntity.ok(passportService.getTokenByPassport(passport));
    } catch (AuthenticationException loginFailed) {
      SecurityContextHolder.clearContext();
      if (loginFailed instanceof LockedException) {
        exceptions.error(ErrorCodes.INCORRECT_CREDENTIALS_EXCEPTION);
      }

      if (loginFailed instanceof AccountStatusException) {
        exceptions.error(ErrorCodes.INCORRECT_CREDENTIALS_EXCEPTION);
      }
      return ResponseEntity.status(403).body(Result.of(0).fail(loginFailed.getMessage()));
    } catch (Exception e) {
      exceptions.error(ErrorCodes.INCORRECT_CREDENTIALS_EXCEPTION);
    }
    return null;
  }
}
