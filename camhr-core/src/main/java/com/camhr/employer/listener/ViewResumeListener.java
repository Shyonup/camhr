package com.camhr.employer.listener;

import com.camhr.employer.events.ViewResumeEvent;
import com.camhr.employer.service.EmployerResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class ViewResumeListener {

  @Autowired
  private EmployerResumeService employerResumeService;

  @EventListener
  public void onViewResumeEvent(ViewResumeEvent viewResumeEvent) {
    employerResumeService.addEemployerHits(viewResumeEvent.getResumeId());
  }
}
