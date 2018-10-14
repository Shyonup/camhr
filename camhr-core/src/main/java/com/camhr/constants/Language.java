package com.camhr.constants;

import java.util.Locale;

public enum Language {
  kh(new Locale("DEFAULT")),
  zh(Locale.SIMPLIFIED_CHINESE),
  en(Locale.US);

  private final Locale locale;

  Language(Locale locale) {
    this.locale=locale;
  }

  public Locale getLocale() {
    return locale;
  }

   public static Language localeOf(Locale locale){
    for(Language l:values()){
      if(l.getLocale().equals(locale)){
        return l;
      }
    }
    return kh;
   }

  public static Language nameOf(String language){
    for(Language l:values()){
      if(l.name().equals(language)){
        return l;
      }
    }
    return kh;
  }
}
