package com.camhr.im;


import com.camhr.CamhrProperties;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RestTemplateTest {

  @Autowired
  private CamhrProperties camhrProperties;

  @Autowired
  private RestTemplate template;

  @Test
  public void sendFormUrlParamters() {
    String url = camhrProperties.getIm().getEndpoint() + "/user/create.action";

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("accid", "yy");

    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(map, null);
    try {
      String response = template.postForObject(url, requestEntity, String.class);
      System.out.println("origin result:" + response);
      JSONObject jsonObject = new JSONObject(response);
      System.out.println(jsonObject.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
