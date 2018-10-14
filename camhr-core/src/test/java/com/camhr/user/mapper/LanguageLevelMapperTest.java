package com.camhr.user.mapper;

import com.camhr.resume.entity.LanguageLevel;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LanguageLevelMapperTest {

  @Autowired
  private LanguageLevelMapper languageLevelMapper;

  @Test
  public void addLanguageLevel() {
    int row = languageLevelMapper.addLanguageLevel(languageLevel());
    Assert.assertEquals(row, 1);
  }

  @Test
  public void deleteLanguageLevel() {
    LanguageLevel languageLevel = languageLevel();
    languageLevelMapper.addLanguageLevel(languageLevel);
    int row = languageLevelMapper.deleteLanguageLevel(languageLevel.getId());
    Assert.assertEquals(1, row);
  }


  @Test
  public void updateLanguageLevel() {
    LanguageLevel languageLevel = languageLevel();
    languageLevelMapper.addLanguageLevel(languageLevel);
    languageLevel.setLanguageId(33);
    int row = languageLevelMapper.updateLanguageLevel(languageLevel);
    Assert.assertEquals(row, 1);
  }

  @Test
  public void getLanguageLevelsByResumeId() {
    LanguageLevel languageLevel = languageLevel();
    languageLevelMapper.addLanguageLevel(languageLevel);
    List<LanguageLevel> list = languageLevelMapper
        .getLanguageLevelsByResumeId(languageLevel.getResumeId());
    Assert.assertTrue(list.size() > 0);
  }

  public LanguageLevel languageLevel() {
    LanguageLevel languageLevel = new LanguageLevel();
    languageLevel.setId(1);
    languageLevel.setResumeId(2);
    languageLevel.setLanguageId(2);
    languageLevel.setLanguageLevelId(3);
    return languageLevel;
  }
}
