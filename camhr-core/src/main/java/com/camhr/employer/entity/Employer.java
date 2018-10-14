package com.camhr.employer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import we.json.AsConstant;

public class Employer {

  private long employerId;
  private String name;
  @JsonIgnore
  private String password;
  private String regionCode;
  private String mobile;
  private String email;
  @ApiModelProperty(notes = "公司名称")
  @NotBlank
  private String company;
  private int contactId;
  @ApiModelProperty(notes = "公司图片地址")
  private List<String> images;
  @ApiModelProperty(notes = "公司logo")
  private String logo;
  @ApiModelProperty(notes = "公司介绍")
  private String description;
  @ApiModelProperty(notes = "公司行业，常量：INDUSTRIAL")
  @AsConstant("INDUSTRIAL")
  @Min(1)
  private int industrialId;
  @ApiModelProperty(notes = "公司规模")
  @AsConstant("EMPLOYER_SCALE")
  private int scaleId;  //与ui的设计有冲突
  @ApiModelProperty(notes = "公司网站")
  private String webUrl;
  @ApiModelProperty(notes = "公司地址")
  private String address;
  @ApiModelProperty(hidden = true)
  @AsConstant("EMPLOYER_STATUS")
  private int status;
  private String language;
  @ApiModelProperty(hidden = true)
  private String googleLatitude;
  @ApiModelProperty(hidden = true)
  private String googleLongitude;
  @ApiModelProperty(notes = "区域 常量：LOCATION")
  @Min(1)
  @AsConstant("LOCATION")
  private int locationId;
  @ApiModelProperty(notes = "公司性质 常量：ETYPE")
  @Min(1)
  @AsConstant("ETYPE")
  private int companyType;
  @ApiModelProperty(notes = "公司福利")
  private List<String> welfare;

  private Date featureTo; // 著名结束时间

  // ==================================== get / set ====================================

  public int getContactId() {
    return contactId;
  }

  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getEmployerId() {
    return employerId;
  }

  public void setEmployerId(long employerId) {
    this.employerId = employerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getIndustrialId() {
    return industrialId;
  }

  public void setIndustrialId(int industrialId) {
    this.industrialId = industrialId;
  }

  public int getScaleId() {
    return scaleId;
  }

  public void setScaleId(int scaleId) {
    this.scaleId = scaleId;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getGoogleLatitude() {
    return googleLatitude;
  }

  public void setGoogleLatitude(String googleLatitude) {
    this.googleLatitude = googleLatitude;
  }

  public String getGoogleLongitude() {
    return googleLongitude;
  }

  public void setGoogleLongitude(String googleLongitude) {
    this.googleLongitude = googleLongitude;
  }

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public int getCompanyType() {
    return companyType;
  }

  public void setCompanyType(int companyType) {
    this.companyType = companyType;
  }

  public List<String> getWelfare() {
    return welfare;
  }

  public void setWelfare(List<String> welfare) {
    this.welfare = welfare;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  public Date getFeatureTo() {
    return featureTo;
  }

  public void setFeatureTo(Date featureTo) {
    this.featureTo = featureTo;
  }
}
