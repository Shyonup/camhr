package com.camhr.user.controller;

import com.camhr.job.builders.JobApplyQueryBuilder;
import com.camhr.job.service.JobApplyService;
import com.camhr.resume.entity.CareerProfile;
import com.camhr.resume.entity.Education;
import com.camhr.resume.entity.Experience;
import com.camhr.resume.entity.Intention;
import com.camhr.resume.entity.LanguageLevel;
import com.camhr.resume.entity.Qualification;
import com.camhr.resume.entity.Resume;
import com.camhr.resume.entity.Seeker;
import com.camhr.resume.entity.Skill;
import com.camhr.user.service.EducationService;
import com.camhr.user.service.ExperienceService;
import com.camhr.user.service.IntentionService;
import com.camhr.user.service.LanguageLevelService;
import com.camhr.user.service.QualificationService;
import com.camhr.user.service.SkillService;
import com.camhr.user.service.UserResumeService;
import com.camhr.user.service.SeekerService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import we.lang.Exceptions;
import we.security.annotation.CurrentUser;
import we.security.rbac.User;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/users/resumes")
@Validated
public class UserResumeController {

  @Autowired
  private EducationService educationService;

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private IntentionService intentionService;

  @Autowired
  private SeekerService seekerService;

  @Autowired
  private UserResumeService userResumeService;

  @Autowired
  private LanguageLevelService languageLevelService;

  @Autowired
  private SkillService skillService;

  @Autowired
  private QualificationService qualificationService;

  @Autowired
  private JobApplyService jobApplyService;

  @GetMapping
  @ApiOperation(value = "获取个人简历列表", response = Resume.class, responseContainer = "List")
  public Result findResumes(@ApiIgnore @CurrentUser User user) {
    return Result.data(userResumeService.getResumesBySeekerId(user.getUserId()));
  }

  @GetMapping("{resumeId}")
  @ApiOperation(value = "获取单个简历,简历预览", response = Resume.class)
  public Result getResumeByResumeId(@PathVariable("resumeId") long resumeId) {
    return Result.data(userResumeService.getResumeByResumeId(resumeId));
  }

  @PostMapping
  @ApiOperation(value = "增加简历")
  public Result addResume(@ApiIgnore @CurrentUser User user,
      @RequestBody @Valid Resume resume) {
    resume.setSeekerId(user.getUserId());
    return Result.of(userResumeService.addUserResume(resume))
        .success("{resumes.add.resume.success}").fail("{resumes.add.resume.fail}");
  }

  //TODO 用户如果没有简历，默认创建一份简历 ，用户如果没有提供简历编号，默认选择最近更新的简历
  @PutMapping("/{resumeId}/profile")
  @ApiOperation(value = "更新个人信息")
  public Result updateProfile(@ApiIgnore @CurrentUser User user,
      @PathVariable("resumeId") long resumeId,
      @RequestBody @Valid Seeker resumeProfile) {
    resumeProfile.setSeekerId(user.getUserId());
    return Result.of(seekerService.updateSeeker(resumeProfile))
        .success("{resumes.update.resumeProfile.success}")
        .fail("{resumes.update.resumeProfile.fail}");
  }

  @PutMapping("/intentions")
  @ApiOperation(value = "更新求职意向")
  public Result updateIntentions(@ApiIgnore @CurrentUser User user,
      @RequestBody @Valid Intention intention) {
    intention.setSeekerId(user.getUserId());
    return Result.of(intentionService.updateIntension(intention))
        .success("{resumes.update.intentions.success}")
        .fail("{resumes.update.intentions.fail}");
  }

  @GetMapping("/intentions")
  @ApiOperation(value = "获取求职意向列表")
  public Result getIntentions(@ApiIgnore @CurrentUser User user) {
    return Result.data(intentionService.findIntentionsBySeekerId(user.getUserId()));
  }

  @PutMapping("/intentions/work/{status}")
  @ApiOperation(value = "更新求职意向管理的求职状态")
  public Result updateIntentionStatus(@ApiIgnore @CurrentUser User user,
      @PathVariable("status") @Valid @Min(1) @Max(2) int status) {
    return Result.of(seekerService.updateWorkStatus(status,user.getUserId()))
        .success("{resumes.update.work.status.success}")
        .fail("{resumes.update.work.status.fail}");
  }

  @PostMapping("/{resumeId}/educations")
  @ApiOperation(value = "添加教育背景")
  public Result addEducations(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid Education education) {
    education.setResumeId(resumeId);
    return Result.of(educationService.addEducation(education))
        .success("{resumes.add.education.success}")
        .fail("{resumes.add.education.fail}");
  }

  @PutMapping("/{resumeId}/educations/{eduId}")
  @ApiOperation(value = "更新教育背景")
  public Result updateEducations(@PathVariable("resumeId") long resumeId,
      @PathVariable("eduId") int eduId,
      @RequestBody @Valid Education education) {
    education.setResumeId(resumeId);
    education.setEduId(eduId);
    return Result.of(educationService.updateEducation(education))
        .success("{resumes.update.education.success}")
        .fail("{resumes.update.education.fail}");
  }

  @DeleteMapping("/{resumeId}/educations/{eduId}")
  @ApiOperation(value = "删除教育背景")
  public Result deleteEducations(@PathVariable("resumeId") long resumeId,
      @PathVariable("eduId") int eduId) {
    return Result.of(educationService.deleteEducation(eduId))
        .success("{resumes.delete.education.success}")
        .fail("{resumes.delete.education.fail}");
  }


  @PostMapping("/{resumeId}/experiences")
  @ApiOperation(value = "添加工作经历")
  public Result addExperiences(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid Experience experience) {
    experience.setResumeId(resumeId);
    return Result.of(experienceService.addExperience(experience))
        .success("{resumes.add.experience.success}")
        .fail("{resumes.add.experience.fail}");
  }

  @DeleteMapping("/{resumeId}/experiences/{experienceId}")
  @ApiOperation(value = "删除工作经历")
  public Result deleteExperiences(@PathVariable("resumeId") long resumeId,
      @PathVariable("experienceId") int experienceId) {
    return Result.of(experienceService.deleteExperience(experienceId))
        .success("{resumes.delete.experience.success}")
        .fail("{resumes.delete.experience.fail}");
  }

  @PutMapping("/{resumeId}/experiences/{experienceId}")
  @ApiOperation(value = "更新工作经历")
  public Result updateExperiences(@PathVariable("resumeId") long resumeId,
      @PathVariable("experienceId") int experienceId,
      @RequestBody @Valid Experience experience) {
    experience.setExperienceId(experienceId);
    return Result.of(experienceService.updateExperience(experience))
        .success("{resumes.update.experience.success}")
        .fail("{resumes.update.experience.fail}");
  }

  @DeleteMapping("/{resumeId}")
  @ApiOperation(value = "删除我的简历")
  public Result removeResume(@PathVariable("resumeId") long resumeId) {
    return Result.of(userResumeService.deleteResumeById(resumeId))
        .success("${resumes.remove.success}")
        .fail("${resumes.remove.fail}");
  }

  @ApiOperation(value = "添加兴趣爱好，培训经历，自我评价，简历完整度,修改简历名称")
  @PutMapping("/{resumeId}/attributes")
  public Result updateResumeAttributes(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid Resume resume) {
    resume.setResumeId(resumeId);
    return Result.of(userResumeService.updateResumeAttributes(resume))
        .success("{resumes.update.success}")
        .fail("{resumes.update.fail}");
  }

  @ApiOperation(value = "新增语言水平")
  @PostMapping("/{resumeId}/languageLevels")
  public Result addLanguageLevel(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid LanguageLevel languageLevel) {
    languageLevel.setResumeId(resumeId);
    return Result.of(languageLevelService.addLanguageLevel(languageLevel))
        .success("{languageLevel.add.success}")
        .fail("{languageLevel.add.fail}");
  }

  @ApiOperation(value = "更新语言水平")
  @PutMapping("/{resumeId}/languageLevels/{languageLevelId}")
  public Result updateLanguageLevel(@PathVariable("languageLevelId") long languageLevelId,
      @RequestBody @Valid LanguageLevel languageLevel) {
    languageLevel.setId(languageLevelId);
    return Result.of(languageLevelService.updateLanguageLevel(languageLevel))
        .success("{languageLevel.update.success}")
        .fail("{languageLevel.update.fail}");
  }

  @ApiOperation(value = "删除语言水平")
  @DeleteMapping("/{resumeId}/languageLevels/{languageLevelId}")
  public Result deleteLanguageLevel(@PathVariable long languageLevelId) {
    return Result.of(languageLevelService.deleteLanguageLevel(languageLevelId))
        .success("{languageLevel.delete.success}")
        .fail("{languageLevel.delete.fail}");
  }

  @ApiOperation(value = "添加技能")
  @PostMapping("/{resumeId}/skills")
  public Result addSkill(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid Skill skill) {
    skill.setResumeId(resumeId);
    return Result.of(skillService.addSkill(skill))
        .success("{skill.add.success}")
        .fail("{skill.add.fail}");
  }

  @ApiOperation(value = "更新技能")
  @PutMapping("/{resumeId}/skills/{skillId}")
  public Result updateSkill(@PathVariable("skillId") long skillId,
      @RequestBody @Valid Skill skill) {
    skill.setId(skillId);
    return Result.of(skillService.updateSkill(skill))
        .success("{skill.update.success}")
        .fail("{skill.update.fail}");
  }

  @ApiOperation(value = "删除技能")
  @DeleteMapping("/{resumeId}/skills/{skillId}")
  public Result addSkill(@PathVariable("skillId") long skillId) {
    return Result.of(skillService.deleteSkill(skillId))
        .success("{skill.delete.success}")
        .fail("{skill.delete.fail}");
  }

  @ApiOperation(value = "添加证书")
  @PostMapping("/{resumeId}/qualifications")
  public Result addQualification(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid Qualification qualification) {
    qualification.setResumeId(resumeId);
    return Result.of(qualificationService.addQualification(qualification))
        .success("{qualification.add.success}")
        .fail("{qualification.add.fail}");
  }

  @PutMapping("/{resumeId}/qualifications/{qualificationId}")
  @ApiOperation(value = "更新证书")
  public Result updateQualification(@PathVariable("qualificationId") long qualificationId,
      @RequestBody @Valid Qualification qualification) {
    qualification.setId(qualificationId);
    return Result.of(qualificationService.updateQualification(qualification))
        .success("{qualification.update.success}")
        .fail("{qualification.update.fail}");
  }

  @DeleteMapping("/{resumeId}/qualifications/{qualificationId}")
  @ApiOperation(value = "删除证书")
  public Result deleteQualification(@PathVariable("qualificationId") long qualificationId) {
    return Result.of(qualificationService.deleteQualification(qualificationId))
        .success("{qualification.delete.success}")
        .fail("{qualification.delete.fail}");
  }

  @PutMapping("/{resumeId}/career/profile")
  @ApiOperation(value = "更新职业概况")
  public Result updateCareerProfile(@PathVariable("resumeId") long resumeId,
      @RequestBody @Valid CareerProfile careerProfile) {
    careerProfile.setResumeId(resumeId);
    return Result.of(userResumeService.updateCareerProfile(careerProfile))
        .success("{resumes.update.success}")
        .fail("{resumes.update.fail}");
  }

  @PostMapping("{employerId}/images")
  @ApiOperation(value = "证书上传")
  public Result uploadImages(@PathVariable("employerId") long employerId,
      MultipartFile file) throws IOException {
    return Result.data(userResumeService.uploadImages(file.getOriginalFilename(), file.getBytes()));
  }

  @GetMapping("/viewers")
  @ApiOperation("谁看过我的简历")
  public Result queryJobApplyReplies(@ApiIgnore @CurrentUser User user,
      JobApplyQueryBuilder jobApplyQueryBuilder) {
    jobApplyQueryBuilder.setSeekerId(user.getUserId());
    return Result.data(jobApplyService.queryJobApplyReplies(jobApplyQueryBuilder));
  }
}
