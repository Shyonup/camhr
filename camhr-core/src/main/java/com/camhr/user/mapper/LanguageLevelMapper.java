package com.camhr.user.mapper;

import com.camhr.resume.entity.LanguageLevel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LanguageLevelMapper {

  int deleteLanguageLevel(long id);

  int addLanguageLevel(LanguageLevel languageLevels);

  int updateLanguageLevel(LanguageLevel languageLevels);

  List<LanguageLevel> getLanguageLevelsByResumeId(long resumeId);
}
