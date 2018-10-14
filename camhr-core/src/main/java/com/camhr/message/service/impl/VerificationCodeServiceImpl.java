package com.camhr.message.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.camhr.CamhrProperties;
import com.camhr.im.utils.RandomUtil;
import com.camhr.message.constants.PrincipalType;
import com.camhr.message.entity.VerificationCode;
import com.camhr.message.mapper.VerificationCodeMapper;
import com.camhr.message.service.VerificationCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import we.lang.Exceptions;
import we.lang.Q;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

  private static final Logger logger = LoggerFactory.getLogger(VerificationCodeService.class);

  @Autowired
  private VerificationCodeMapper verificationCodeMapper;

  @Autowired
  private CamhrProperties camhrProperties;

  @Override
  public String generateCode() {
    return null;
  }

  @Override
  public int addVerificationCode(VerificationCode verificationCode) {
    return 0;
  }

  @Override
  public void checkVerificationCode(String principal, String verifyCode) {
    VerificationCode verificationCode = verificationCodeMapper.getVerificationCode(principal);
    if (verificationCode == null || !StringUtils.equals(verifyCode, verificationCode.getCode())) {
//      Exceptions.of(VerificationCodeService.class).error(PassportErrorCodes.CODE_INVAILD);
    }
    if (verificationCode.isValidated()) {
//      Exceptions.of(VerificationCodeService.class).error(PassportErrorCodes.CODE_VALIDATED);
    }
    if (verificationCode.getExpiredAt() < Q.now()) {
//      Exceptions.of(VerificationCodeService.class).error(PassportErrorCodes.CODE_EXPIRED);
    }
    verificationCode.setUpdateAt(Q.now());
    verificationCode.setValidated(true);
    verificationCodeMapper.updateVerificationCode(verificationCode);
  }


  @Override
  public boolean sendPhoneCode(String phone) throws Exception {
    String code = RandomUtil
        .randomNumber(camhrProperties.getSms().getVerificationCodeLength());
    SendSmsResponse sendSmsResponse = sendSMS(phone, code);
    if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
      //记录verificationCode
      VerificationCode verificationCode = new VerificationCode();
      verificationCode.setValidated(false);
      verificationCode.setCreateAt(Q.now());
      verificationCode.setLastSendAt(Q.now());
      verificationCode.setPrincipal(phone);
      verificationCode.setType(PrincipalType.EMAIL.value());
      verificationCode.setExpiredAt(Q.now() + 300);
      verificationCode.setCode(code);
      int rows = verificationCodeMapper.addVerificationCode(verificationCode);
      if (rows > 0) {
        return true;
      }
    } else {
      Exceptions.of(VerificationCodeService.class)
          .error(HttpStatus.INTERNAL_SERVER_ERROR.value(), sendSmsResponse.getMessage());
    }
    return false;
  }

  private SendSmsResponse sendSMS(String phone, String code) throws ClientException {
    //设置超时时间-可自行调整
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    //初始化ascClient需要的几个参数
    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    //替换成你的AK
    final String accessKeyId = camhrProperties.getSms()
        .getAliyunAccessKeyId();//你的accessKeyId,参考本文档步骤2
    final String accessKeySecret = camhrProperties.getSms()
        .getAliyunAccessKeySecret();//你的accessKeySecret，参考本文档步骤2
    //初始化ascClient,暂时不支持多region（请勿修改）
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
        accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);
    //组装请求对象
    SendSmsRequest request = new SendSmsRequest();
    //使用post提交
    request.setMethod(MethodType.POST);
    //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
    request.setPhoneNumbers(phone);
    //必填:短信签名-可在短信控制台中找到
    request.setSignName(camhrProperties.getSms().getAliyunSmsSignName());
    //必填:短信模板-可在短信控制台中找到
    request.setTemplateCode(camhrProperties.getSms().getAliyunSmsTemplateCode());
    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
    request.setTemplateParam("{\"code\":\"" + code + "\"}");
    //请求失败这里会抛ClientException异常
    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
    return sendSmsResponse;
  }

}
