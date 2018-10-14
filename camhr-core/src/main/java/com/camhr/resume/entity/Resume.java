package com.camhr.resume.entity;

import com.google.common.collect.Lists;
import java.util.List;

public class Resume extends Cv {

  private Seeker profile = new Seeker();
  private Intention intention = new Intention();
  private List<Education> educations = Lists.newArrayList();  //教育经历
  private List<Experience> experiences = Lists.newArrayList(); //工作经历
  private List<Skill> skills = Lists.newArrayList();//技能
  private List<Qualification> qualifications = Lists.newArrayList();//证书
  private List<LanguageLevel> languageLevels = Lists.newArrayList();//语言水平

  public Seeker getProfile() {
    return profile;
  }

  public void setProfile(Seeker profile) {
    this.profile = profile;
  }

  public Intention getIntention() {
    return intention;
  }

  public void setIntention(Intention intention) {
    this.intention = intention;
  }

  public List<Education> getEducations() {
    return educations;
  }

  public void setEducations(List<Education> educations) {
    this.educations = educations;
  }

  public List<Experience> getExperiences() {
    return experiences;
  }

  public void setExperiences(List<Experience> experiences) {
    this.experiences = experiences;
  }

  public List<Skill> getSkills() {
    return skills;
  }

  public void setSkills(List<Skill> skills) {
    this.skills = skills;
  }

  public List<Qualification> getQualifications() {
    return qualifications;
  }

  public void setQualifications(List<Qualification> qualifications) {
    this.qualifications = qualifications;
  }

  public List<LanguageLevel> getLanguageLevels() {
    return languageLevels;
  }

  public void setLanguageLevels(List<LanguageLevel> languageLevels) {
    this.languageLevels = languageLevels;
  }
}
