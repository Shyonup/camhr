package com.camhr.user.service;

import com.camhr.resume.entity.LanguageLevel;
import java.util.List;

public interface LanguageLevelService {

  int updateLanguageLevel(LanguageLevel languageLevel);

  int addLanguageLevel(LanguageLevel languageLevel);

  int deleteLanguageLevel(long id);

  List<LanguageLevel> getLanguageLevelsByResumeId(long resumeId);
}
