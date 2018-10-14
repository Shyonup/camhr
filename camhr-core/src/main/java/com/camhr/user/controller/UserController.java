package com.camhr.user.controller;

import com.camhr.authentication.ChangePassword;
import com.camhr.authentication.PassportController;
import com.camhr.constants.AccountType;
import com.camhr.constants.Language;
import com.camhr.user.entity.SeekerMobileRegParam;
import com.camhr.user.entity.SeekerRegParam;
import com.camhr.user.error.UserErrorCodes;
import com.camhr.user.service.SeekerService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.lang.Exceptions;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/users")
@Validated
public class UserController extends PassportController {

  @Autowired
  private SeekerService seekerService;

  @PostMapping("/register")
  public ResponseEntity register(@Valid SeekerRegParam seekerRegParam) {
    seekerRegParam.setLanguage(Language.localeOf(LocaleContextHolder.getLocale()).name());
    if (seekerService.registerSeeker(seekerRegParam) < 1) {
      Exceptions.of(SeekerService.class).error(UserErrorCodes.REGISTER_FAIL);
    }
    return authorize(seekerRegParam.getEmail(), seekerRegParam.getPassword(), AccountType.SEEKER);
  }

  @PostMapping("/register/mobile")
  public ResponseEntity registerByMobile(@Valid SeekerMobileRegParam mobileRegParam) {
    mobileRegParam.setLanguage(Language.localeOf(LocaleContextHolder.getLocale()).name());
    if (seekerService.registerSeekerByMobile(mobileRegParam) < 1) {
      Exceptions.of(SeekerService.class).error(UserErrorCodes.REGISTER_FAIL);
    }
    return authorizeMobile(mobileRegParam.getMobile(), mobileRegParam.getRegionCode(),
        AccountType.SEEKER);
  }

  @PostMapping("/sms/verify-code")
  public Result sendVerifyCode(@RequestParam @NotBlank @Min(1) String mobile,
      @RequestParam @NotBlank @Min(1) String regionCode) {
    //todo 接入阿里云短信
    return Result.of(1).addAttribute("verifyCode", "86");
  }

  @PostMapping("/authorize")
  public ResponseEntity authorize(@NotNull @NotBlank @RequestParam String name,
      @NotNull @RequestParam String password) {
    return authorize(name, password, AccountType.SEEKER);
  }

  @GetMapping("/me")
  public Result getSeeker(@ApiIgnore @CurrentUser User user) {
    return Result.data(seekerService.getSeekerById(user.getUserId()));
  }

  @PutMapping("/password")
  @ApiOperation(value = "change password", notes = "change password")
  public Result changePassword(@ApiIgnore @CurrentUser User user,
      @Valid @RequestBody ChangePassword changePassword) {
    return Result
        .of(seekerService.changePassportPassword(user.getUserId(), changePassword.getOldPassword(),
            changePassword.getNewPassword()))
        .success("{password.update.success}").fail("{password.update.fail}");
  }

  @PutMapping("/language")
  public Result setDefaultLanguage(@ApiIgnore @CurrentUser User user,
      @RequestParam("language") @NotBlank @Length(min = 1) String language) {
    return Result
        .of(seekerService.setDefaultLanguage(Language.nameOf(language).name(), user.getUserId()))
        .success("${user.language.set.success:success}")
        .fail("${user.language.set.fail:fail}");
  }
}
