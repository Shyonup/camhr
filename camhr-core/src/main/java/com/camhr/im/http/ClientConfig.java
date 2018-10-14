package com.camhr.im.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

  @Autowired
  private HeaderInterceptor headerInterceptor;

  @Bean
  @Qualifier("imRestTemplate")
  public RestTemplate restTemplate(){
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(headerInterceptor);
    return restTemplate;
  }
}
