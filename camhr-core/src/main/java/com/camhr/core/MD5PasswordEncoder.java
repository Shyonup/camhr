package com.camhr.core;

import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *   兼容旧系统
 */
public class MD5PasswordEncoder implements PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    return Hashing.md5().hashString(rawPassword, Charset.forName("UTF-8")).toString();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return Hashing.md5().hashString(rawPassword, Charset.forName("UTF-8")).toString()
        .equals(encodedPassword);
  }


}
