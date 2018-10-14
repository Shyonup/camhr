package com.camhr.job.constants;


import java.util.Locale;
import we.annotation.ConstantMapper;
import we.lang.Constant;
import we.lang.Constants;

/**
 * 为了方便信息扩展，代码中只定义需要进行业务判断的常量，也可以定义多个常量<br/> 未定义的常量可能从数据库加载
 */
@ConstantMapper
public enum JobLanguage {
  KH(Constants.of("JOB_LANGUAGE", "kh", "kh", "ខែ្មរ"), new Locale("DEFAULT")), // 给个null是因为，Locale找不到高棉语常量
  ZH(Constants.of("JOB_LANGUAGE", "zh", "zh", "中文"), Locale.SIMPLIFIED_CHINESE),
  EN(Constants.of("JOB_LANGUAGE", "en", "en", "English"), Locale.US);

  private final Constant<String> constant;

  private Locale locale;

  JobLanguage(Constant<String> constant, Locale locale) {
    this.constant = constant;
    this.locale = locale;
  }

  public String value() {
    return this.constant.getValue();
  }

  public static JobLanguage getJobLanguageByLocale(Locale locale) {
    JobLanguage[] values = values();
    for (JobLanguage jobLanguage : values) {
      Locale tmpLocale = jobLanguage.getLocale();
      if (tmpLocale != null && tmpLocale.equals(locale)) {
        return jobLanguage;
      }
    }

    return KH; // 找不到任何值，就默认返回高棉语
  }

  public Locale getLocale() {
    return locale;
  }

}
