package com.camhr.im.service.impl;

import com.camhr.CamhrProperties;
import com.camhr.im.entity.IMAccount;
import com.camhr.im.mapper.IMAccountMapper;
import com.camhr.im.service.IMService;
import com.camhr.im.utils.CheckSumBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import we.lang.IdWorker;
import we.lang.JSON;
import we.lang.Q;

@Service
public class IMServiceImpl implements IMService {

  private static Logger logger = LoggerFactory.getLogger(IMService.class);

  private static IdWorker idWorker = new IdWorker(1, 1, 1);

  private static ExecutorService executors = Executors.newCachedThreadPool();
  private static final String CREATE_USER_PATH = "/user/create.action";
  private static final String UPDATE_USER_PATH = "/user/updateUinfo.action";

  @Autowired
  @Qualifier("imRestTemplate")
  private RestTemplate restTemplate;

  @Autowired
  private IMAccountMapper imAccountMapper;

  @Autowired
  private CamhrProperties camhrProperties;

  @Override
  public int createIMAccount(IMAccount imAccount) {
    //todo 用户登录检查是否有IM账号
    imAccount.setCreateAt(Q.now());
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("accid", imAccount.getAccessId());
    params.add("token", imAccount.getToken());
    params.add("name", imAccount.getNickname());
    params.add("ex", JSON.stringify(imAccount.getExtra()));
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(params, null);
    try {
      String response = restTemplate
          .postForObject(camhrProperties.getIm().getEndpoint() + CREATE_USER_PATH, requestEntity,
              String.class);
      JSONObject jsonObject = new JSONObject(response);
      logger.info("create im account result:" + jsonObject.toString());
      int code = jsonObject.getInt("code");
      if (code == 200) {
        return imAccountMapper.addAccount(imAccount);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return 0;
  }

  @Override
  public boolean updateUserInfo(IMAccount imAccount) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("accid", imAccount.getAccessId());
    params.add("name", imAccount.getNickname());
    params.add("ex", JSON.stringify(imAccount.getExtra()));
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(params, null);
    try {
      String response = restTemplate
          .postForObject(camhrProperties.getIm().getEndpoint() + UPDATE_USER_PATH, requestEntity,
              String.class);
      JSONObject jsonObject = new JSONObject(response);
      logger.info("update im userinfo result:" + jsonObject.toString());
      int code = jsonObject.getInt("code");
      if (code == 200) {
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean existIMAccount(IMAccount imAccount) {
    return imAccountMapper.existAccount(imAccount);
  }

  @Override
  public IMAccount getIMAccount(String name, int accountType) {
    return imAccountMapper.getIMAccount(name, accountType);
  }
}
