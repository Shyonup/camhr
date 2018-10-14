package com.camhr;

import com.camhr.im.IMProperties;
import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "camhr")
public class CamhrProperties {

  private BigDecimal minRechargeAmount = new BigDecimal("1.00");

  private JobProperties job = new JobProperties();

  private IMProperties im = new IMProperties();

  private SmsProperties sms = new SmsProperties();

  public JobProperties getJob() {
    return this.job;
  }

  public void setJob(JobProperties job) {
    this.job = job;
  }

  public IMProperties getIm() {
    return im;
  }

  public void setIm(IMProperties im) {
    this.im = im;
  }

  public static class JobProperties {

  }

  public static class SmsProperties {
    private int verificationCodeLength = 4;
    private String aliyunAccessKeyId;
    private String aliyunAccessKeySecret;
    private String aliyunSmsSignName;
    private String aliyunSmsTemplateCode;

    public int getVerificationCodeLength() {
      return verificationCodeLength;
    }

    public void setVerificationCodeLength(int verificationCodeLength) {
      this.verificationCodeLength = verificationCodeLength;
    }

    public String getAliyunAccessKeyId() {
      return aliyunAccessKeyId;
    }

    public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
      this.aliyunAccessKeyId = aliyunAccessKeyId;
    }

    public String getAliyunAccessKeySecret() {
      return aliyunAccessKeySecret;
    }

    public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
      this.aliyunAccessKeySecret = aliyunAccessKeySecret;
    }

    public String getAliyunSmsSignName() {
      return aliyunSmsSignName;
    }

    public void setAliyunSmsSignName(String aliyunSmsSignName) {
      this.aliyunSmsSignName = aliyunSmsSignName;
    }

    public String getAliyunSmsTemplateCode() {
      return aliyunSmsTemplateCode;
    }

    public void setAliyunSmsTemplateCode(String aliyunSmsTemplateCode) {
      this.aliyunSmsTemplateCode = aliyunSmsTemplateCode;
    }
  }

  public BigDecimal getMinRechargeAmount() {
    return minRechargeAmount;
  }

  public void setMinRechargeAmount(BigDecimal minRechargeAmount) {
    this.minRechargeAmount = minRechargeAmount;
  }

  public SmsProperties getSms() {
    return sms;
  }

  public void setSms(SmsProperties sms) {
    this.sms = sms;
  }
}
