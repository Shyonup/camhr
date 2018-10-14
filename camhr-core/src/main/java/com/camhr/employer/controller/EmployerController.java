package com.camhr.employer.controller;


import com.camhr.authentication.ChangePassword;
import com.camhr.authentication.PassportController;
import com.camhr.constants.AccountType;
import com.camhr.constants.Language;
import com.camhr.employer.entity.Employer;
import com.camhr.employer.entity.EmployerRegParam;
import com.camhr.employer.service.EmployerService;
import com.camhr.user.error.UserErrorCodes;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import we.lang.Exceptions;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/employers")
@Validated
public class EmployerController extends PassportController {

  @Autowired
  private EmployerService employerService;

  @PostMapping("/register")
  public ResponseEntity register(@Valid EmployerRegParam employerRegParam) {
    employerRegParam.setLanguage(Language.localeOf(LocaleContextHolder.getLocale()).name());
    if (employerService.registerEmployer(employerRegParam) < 1) {
      Exceptions.of(EmployerService.class).error(UserErrorCodes.REGISTER_FAIL);
    }
    return authorize(employerRegParam.getName(), employerRegParam.getPassword(),
        AccountType.EMPLOYER);
  }

  @PostMapping("/authorize")
  public ResponseEntity authorize(@NotNull @NotBlank @RequestParam String name,
      @NotNull @RequestParam String password) {
    return authorize(name, password, AccountType.EMPLOYER);
  }


  @GetMapping("/me")
  public Result getEmployer(@ApiIgnore @CurrentUser User user) {
    return Result.data(employerService.getEmployer(user.getUserId()));
  }

  @PutMapping("/language")
  public Result setDefaultLanguage(@ApiIgnore @CurrentUser User user,
      @RequestParam("language") @NotBlank @Length(min = 1) String language) {
    return Result
        .of(employerService.setDefaultLanguage(Language.nameOf(language).name(), user.getUserId()))
        .success("${user.language.set.success:success}")
        .fail("${user.language.set.fail:fail}");
  }

  @PutMapping("/me")
  @ApiOperation(value = "公司主页信息编辑", notes = "公司主页信息编辑")
  public Result updateEmployer(@ApiIgnore @CurrentUser User user,
      @RequestBody Employer employer) {
    employer.setEmployerId(user.getUserId());
    return Result.of(employerService.updateEmployerInfo(employer))
        .success("employer.update.success")
        .fail("employer.update.fail");
  }

  @PostMapping({"/{employerId}/images"})
  @ApiOperation(value = "上传公司图片", notes = "上传公司图片")
  public Result uploadEmployerImages(MultipartFile image)
      throws IOException {
    return Result
        .data(employerService.uploadEmployerImage(image.getOriginalFilename(), image.getBytes()));
  }

  @PostMapping({"/{employerId}/logo"})
  @ApiOperation(value = "上传公司logo", notes = "上传公司logo")
  public Result uploadEmployerLogo(MultipartFile logo)
      throws IOException {
    return Result
        .data(employerService.uploadEmployerLogo(logo.getOriginalFilename(), logo.getBytes()));
  }

  @PostMapping({"/{employerId}/licenses"})
  @ApiOperation(value = "上传公司营业执照", notes = "上传公司营业执照")
  public Result uploadEmployerLicense(@PathVariable @Min(1) int employerId,
      @RequestParam("license") MultipartFile[] licenses) {
    String path = "http://uploadfile.camhr.com/companyphoto/c20090630002817718/d901f44b-fc6f-41ed-a3cb-659065e37c28.jpg;";
    return Result.data(path);
  }

  @PostMapping({"/{employerId}/tax/certificates"})
  @ApiOperation(value = "上传公司税务证明", notes = "上传公司税务证明")
  public Result uploadEmployerCertificate(@PathVariable @Min(1) int employerId,
      @RequestParam("certificate") MultipartFile[] certificates) {
    String path = "http://uploadfile.camhr.com/companyphoto/c20090630002817718/d901f44b-fc6f-41ed-a3cb-659065e37c28.jpg;";
    return Result.data(path);
  }

  @PutMapping("/password")
  @ApiOperation(value = "change password", notes = "change password")
  public Result changePassword(@ApiIgnore @CurrentUser User user,
      @Valid @RequestBody ChangePassword changePassword) {
    return Result
        .of(employerService
            .changePassportPassword(user.getUserId(), changePassword.getOldPassword(),
                changePassword.getNewPassword()))
        .success("{password.update.success}").fail("{password.update.fail}");
  }
}