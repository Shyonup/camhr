package com.camhr.employer.recharge;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import we.payment.PaymentRequest;
import we.payment.PaymentResponse;
import we.payment.mpay.MPaymentRequest;
import we.payment.mpay.utils.SignUtils;

public class RestTemplateTest {


  @Test
  public void test() {
    MPaymentRequest request = new MPaymentRequest();
    request
        .setPayType("ALIPAY");
    request.setPaymentId(System.currentTimeMillis() + "");
    request.setOrderId("JFOISFFF");
    request.setSubject("FFF");
    request.setTotalFee("0.21");
    doPayment(request);
  }

  public PaymentResponse doPayment(PaymentRequest paymentRequest) {
    MPaymentRequest request = (MPaymentRequest) paymentRequest;
    Date now = new Date();
    Map<String,String> parameterMap	= new HashMap<>();
    parameterMap.put("mercId","8888880000019022");
    parameterMap.put("outOrderNo",System.currentTimeMillis()+"");
    parameterMap.put("payType","ALIPAY");
    parameterMap.put("orderAmt","0.12");
    parameterMap.put("ccy","USD");
    parameterMap.put("timestamp",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    parameterMap.put("goodsDetail","DD");
    parameterMap.put("notifyUrl","http://183.6.136.250:8899/learn/alipay");
    parameterMap.put("version","V2.0");
    parameterMap.put("signType","MD5");

    parameterMap = SignUtils.buildParam(parameterMap, "tqStlzcROEoLaTnAzWMFiogABdhVfxNB");

    String mpayResult = HttpClient.executeForm("http://103.242.56.40:9001/upg/v2/createUserScanOrder", parameterMap);

    System.out.println(mpayResult);
    return null;
  }
}