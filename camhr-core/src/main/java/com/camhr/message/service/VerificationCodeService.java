package com.camhr.message.service;

import com.camhr.message.entity.VerificationCode;

public interface VerificationCodeService {

  String generateCode();

  int addVerificationCode(VerificationCode verificationCode);

  void checkVerificationCode(String principal, String verifyCode);

  boolean sendPhoneCode(String phone) throws Exception;
}
