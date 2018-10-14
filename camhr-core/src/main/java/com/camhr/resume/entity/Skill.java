package com.camhr.resume.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Skill {

  @ApiModelProperty(hidden = true)
  private long id;
  @ApiModelProperty(notes = "简历id")
  @Min(1)
  private long resumeId;
  @ApiModelProperty(notes = "简历名称")
  @NotBlank
  private String name;
  @ApiModelProperty(notes = "经验年数")
  @Min(0)
  private int years;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYears() {
    return years;
  }

  public void setYears(int years) {
    this.years = years;
  }
}
