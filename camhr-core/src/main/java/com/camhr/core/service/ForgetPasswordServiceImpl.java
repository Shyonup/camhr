package com.camhr.core.service;

import com.camhr.constants.AccountType;
import com.camhr.employer.service.EmployerService;
import com.camhr.message.constants.PrincipalType;
import com.camhr.message.entity.VerificationCode;
import com.camhr.message.service.VerificationCodeService;
import com.camhr.user.error.UserErrorCodes;
import com.camhr.user.service.SeekerService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import we.lang.Exceptions;
import we.lang.Q;
import we.security.passport.PassportModule;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordServiceImpl.class);

  private ExecutorService emailSenderPool = Executors.newFixedThreadPool(5);

  @Autowired
  private JavaMailSenderImpl mailSender;

  @Autowired
  private VerificationCodeService codeService;

  @Autowired
  private SeekerService seekerService;

  @Autowired
  private EmployerService employerService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public boolean validatePhoneAndSendVerificationCode(String regionCode, String phone) {
    if (!seekerService.isMobileExist(regionCode, phone) && !employerService
        .isMobileExist(regionCode, phone)) {
      Exceptions.of(PassportModule.NAME).error(UserErrorCodes.MOBILE_NOT_EXIST);
    }
    try {
      return codeService.sendPhoneCode(regionCode + phone);
    } catch (Exception e) {
      LOGGER.error("Cannot send verification code, ", e);
      return false;
    }
  }

  @Override
  public boolean validateEmailAndSendEmail(String email) {
    if (!seekerService.isEmailExist(email) && !employerService.isEmailExist(email)) {
      Exceptions.of(PassportModule.NAME).error(UserErrorCodes.EMAIL_NOT_EXIST);
    }
    // 保存验证码
    VerificationCode verificationCode = new VerificationCode();
    verificationCode.setPrincipal(email);
    String code = codeService.generateCode();
    verificationCode.setCode(code);
    verificationCode.setValidated(false);
    verificationCode.setCreateAt(Q.now());
    verificationCode.setType(PrincipalType.EMAIL.value());
    verificationCode.setExpiredAt(Q.now() + 30 * 60); // expire after 30 min
    if (codeService.addVerificationCode(verificationCode) > 0) {
      // 为避免网络延时阻塞，在新线程发邮件
      emailSenderPool
          .execute(
              () -> mailSender.send(simpleMailMessage(email, "CAMHR: Verification Code", code)));
      return true;
    }
    return false;
  }

  @Override
  public boolean verifyPhoneCodeAndUpdatePassword(int accountType, String regionCode, String phone,
      String code, String password) {
    password = passwordEncoder.encode(password);
    if (isPrincipleCodeValid(regionCode + phone, code)) {
      if (AccountType.EMPLOYER.value() == accountType) {
        return employerService
            .updatePasswordByPhoneOrEmail(password, regionCode, phone, null);
      } else if (AccountType.SEEKER.value() == accountType) {
        return seekerService.updatePasswordByPhoneOrEmail(password, regionCode, phone, null);
      }
    }
    return false;
  }

  @Override
  public boolean verifyEmailCodeAndUpdatePassword(int accountType, String email, String code,
      String password) {
    password = passwordEncoder.encode(password);
    if (isPrincipleCodeValid(email, code)) {
      if (AccountType.EMPLOYER.value() == accountType) {
        return employerService.updatePasswordByPhoneOrEmail(password, null, null, email);
      } else if (AccountType.SEEKER.value() == accountType) {
        return seekerService.updatePasswordByPhoneOrEmail(password, null, null, email);
      }
    }
    return false;
  }

  private SimpleMailMessage simpleMailMessage(String sendTo, String subject, String content) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(sendTo);
    mail.setFrom(mailSender.getUsername());
    mail.setSubject(subject);
    mail.setText(content);
    return mail;
  }

  private boolean isPrincipleCodeValid(String principle, String code) {
    try {
      codeService.checkVerificationCode(principle, code);
      return true;
    } catch (Exception e) {
      LOGGER.info(principle + " can not match with verification code " + code);
      return false;
    }
  }

}
