package com.camhr.resume.entity;

public class ReqIndustrial {

  private long id;
  private long resumeId;
  private int industryId;

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

  public int getIndustryId() {
    return industryId;
  }

  public void setIndustryId(int industryId) {
    this.industryId = industryId;
  }
}
