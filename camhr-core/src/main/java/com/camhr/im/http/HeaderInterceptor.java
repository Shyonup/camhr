package com.camhr.im.http;


import com.camhr.CamhrProperties;
import com.camhr.im.utils.CheckSumBuilder;
import com.camhr.im.utils.RandomUtil;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import we.lang.Q;

@Component
public class HeaderInterceptor implements ClientHttpRequestInterceptor {

  @Autowired
  private CamhrProperties camhrProperties;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {

    HttpHeaders headers = request.getHeaders();
    String nonce = RandomUtil.random(camhrProperties.getIm().getRandomLength());
    String curTime = String.valueOf(Q.now());
    String checkSum = CheckSumBuilder
        .getCheckSum(camhrProperties.getIm().getAppSecret(), nonce, curTime);
    // 设置请求的header
    headers.set("AppKey", camhrProperties.getIm().getAppKey());
    headers.set("Nonce", nonce);
    headers.set("CurTime", curTime);
    headers.set("CheckSum", checkSum);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    return execution.execute(request, body);
  }
}
