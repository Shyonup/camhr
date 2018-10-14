package com.camhr.authentication;

import com.camhr.core.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import we.security.authentication.jwt.JwtConfigurer;


@Configuration
@EnableWebSecurity
@Order(200)
public class UserWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Value("${version:v1.0.0}")
  private String version;

  @Autowired
  private DaoAuthenticationProvider userAuthenticationProvider;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(userAuthenticationProvider);
  }

  @Bean
  @Primary
  public PasswordEncoder passwordEncoder(){
    return new MD5PasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider userAuthenticationProvider(
      UserDetailsService userDetailsService) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/" + version + "/users/**")
        .apply(new JwtConfigurer())
        .and()
        .authorizeRequests()
        .anyRequest().authenticated()
        .anyRequest().hasRole("USER");

    http.antMatcher("/" + version + "/employers/**")
        .apply(new JwtConfigurer())
        .and()
        .authorizeRequests()
        .anyRequest().authenticated()
        .anyRequest().hasRole("ENTERPRISE");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/" + version + "/users/authorize");
  }
}
