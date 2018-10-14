package com.camhr.employer.events;

public class ViewResumeEvent {

  private long resumeId;

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  public ViewResumeEvent(long resumeId) {
    this.resumeId = resumeId;
  }
}
