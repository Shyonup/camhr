package com.camhr.core.service;

public interface ForgetPasswordService {

  boolean validatePhoneAndSendVerificationCode(String regionCode, String phone);

  boolean validateEmailAndSendEmail(String email);

  boolean verifyPhoneCodeAndUpdatePassword(int accountType, String regionCode, String phone,
      String code, String password);

  boolean verifyEmailCodeAndUpdatePassword(int accountType, String email, String code,
      String password);

}
