package com.camhr.user.service;

import com.camhr.resume.entity.Intention;
import java.util.List;

public interface IntentionService {

  int updateIntension(Intention intention);

  Intention getIntention(long resumeId);

  List<Intention> findIntentionsBySeekerId(long seekerId);
}
