package com.camhr.user.service.impl;

import com.camhr.resume.entity.LanguageLevel;
import com.camhr.user.mapper.LanguageLevelMapper;
import com.camhr.user.service.LanguageLevelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class LanguageLevelServiceImpl implements LanguageLevelService {

  @Autowired
  private LanguageLevelMapper languageLevelMapper;

  @Override
  public int updateLanguageLevel(LanguageLevel languageLevel) {
    return languageLevelMapper.updateLanguageLevel(languageLevel);
  }

  @Override
  public int addLanguageLevel(LanguageLevel languageLevel) {
    return languageLevelMapper.addLanguageLevel(languageLevel);
  }

  @Override
  public int deleteLanguageLevel(long id) {
    return languageLevelMapper.deleteLanguageLevel(id);
  }

  @Override
  public List<LanguageLevel> getLanguageLevelsByResumeId(long resumeId) {
    return languageLevelMapper.getLanguageLevelsByResumeId(resumeId);
  }
}
