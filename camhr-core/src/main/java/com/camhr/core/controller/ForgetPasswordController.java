package com.camhr.core.controller;

import com.camhr.core.entity.ResetPassword;
import com.camhr.core.service.ForgetPasswordService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/findpassword")
@Validated
public class ForgetPasswordController {

  @Autowired
  private ForgetPasswordService forgetPasswordService;

  @ApiOperation(value = "validate phone and send verification code")
  @GetMapping("/{regionCode}/{phone}")
  public Result sendVerificationCode(@PathVariable @NotBlank String regionCode,
      @PathVariable @NotBlank String phone) {
    return Result.of(forgetPasswordService.validatePhoneAndSendVerificationCode(regionCode, phone))
        .success("{forget.password.send.code.success}").fail("{forget.password.send.code.fail}");
  }

  @ApiOperation(value = "validate email and send verification code")
  @GetMapping("/{email}")
  public Result sendVerificationCode(@PathVariable @NotBlank String email) {
    return Result.of(forgetPasswordService.validateEmailAndSendEmail(email))
        .success("{forget.password.send.code.success}").fail("{forget.password.send.code.fail}");
  }

  @ApiOperation(value = "verify phone, code then update password")
  @PostMapping("/{accountType}/{regionCode}/{phone}")
  public Result resetPasswordByPhone(@PathVariable int accountType,
      @PathVariable @NotBlank String regionCode,
      @PathVariable @NotBlank String phone, @Valid @RequestBody ResetPassword resetPassword) {
    return Result.of(forgetPasswordService
        .verifyPhoneCodeAndUpdatePassword(accountType, regionCode, phone, resetPassword.getVerificationCode(),
            resetPassword.getPassword())).success("{password.update.success}")
        .fail("{password.update.fail}");
  }

  @ApiOperation(value = "verify email, code then update password")
  @PostMapping("/{accountType}/{email}")
  public Result resetPasswordByEmail(@PathVariable int accountType,
      @PathVariable @NotBlank String email,
      @RequestBody ResetPassword resetPassword) {
    return Result.of(forgetPasswordService
        .verifyEmailCodeAndUpdatePassword(accountType, email, resetPassword.getVerificationCode(),
            resetPassword.getPassword())).success("{password.update.success}")
        .fail("{password.update.fail}");
  }

}
