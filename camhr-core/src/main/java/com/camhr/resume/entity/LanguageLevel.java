package com.camhr.resume.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import we.json.AsConstant;

public class LanguageLevel {

  private long id;
  @ApiModelProperty(notes = "简历id")
  @Min(1)
  private long resumeId;
  @AsConstant("LANGUAGE")
  @ApiModelProperty(notes = "语言，常量：LANGUAGE")
  @Min(1)
  private int languageId;
  @AsConstant("LANGUAGE_LEVEL")
  @ApiModelProperty(notes = "熟练程度,常量：LANGUAGE_LEVEL")
  @Min(1)
  private int languageLevelId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  public int getLanguageId() {
    return languageId;
  }

  public void setLanguageId(int languageId) {
    this.languageId = languageId;
  }

  public int getLanguageLevelId() {
    return languageLevelId;
  }

  public void setLanguageLevelId(int languageLevelId) {
    this.languageLevelId = languageLevelId;
  }
}
