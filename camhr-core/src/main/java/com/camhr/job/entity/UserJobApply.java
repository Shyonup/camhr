package com.camhr.job.entity;

import com.camhr.job.utils.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import we.json.AsConstant;

/**
 * <pre>
 * 用户端查询JobApply信息。
 * UserJobApply实体类 和 JobApply实体类 相比，只是多了几个外键数据（就是拿到外键后，到其他表查值）
 *
 * Created by Allen on 2018/9/29.
 * </pre>
 */
public class UserJobApply extends JobApply {

  // ------ 【employer信息】 查询的时候，通过LEFT JOIN E_EMPLOYER 获取数据 ------
  private String employerLogo; // 公司logo

  private String company; // 公司名称

  private String companyAddress; // 公司地址

  // ------ 【Job信息】 查询的时候，通过LEFT JOIN E_JOB 获取数据 ------
  @AsConstant("SALARY")
  private int salaryId; // 薪资范围，外键，常量：SARALY

  @AsConstant("CATEGORY")
  private int categoryId; // 岗位，常量：CATEGORY，前端自行翻译，因为涉及国际化的问题

  @ApiModelProperty(value = "应聘职位的名称", readOnly = true)
  private String jobTitle;

  // ------ 【seeker信息】 查询的时候，通过LEFT JOIN U_SEEKER 获取数据 ------
  @ApiModelProperty(value = "求职者姓名", readOnly = true)
  private String intervieweeName;

  @ApiModelProperty(value = "性别，常量：SEX", readOnly = true)
  @AsConstant("SEX")
  private int sexId;

  @ApiModelProperty(value = "年龄", readOnly = true)
  private int age; // 通过 birthday属性（出生日期）计算出来

  @ApiModelProperty(value = "电话号码", readOnly = true)
  private String phoneNumber;

  @ApiModelProperty(value = "出生日期", hidden = true)
  private Date birthday; // 用来计算年龄

  // ==================================== get / set ====================================

  public String getEmployerLogo() {
    return employerLogo;
  }

  public void setEmployerLogo(String employerLogo) {
    this.employerLogo = employerLogo;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompanyAddress() {
    return companyAddress;
  }

  public void setCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
  }

  public int getSalaryId() {
    return salaryId;
  }

  public void setSalaryId(int salaryId) {
    this.salaryId = salaryId;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getIntervieweeName() {
    return intervieweeName;
  }

  public void setIntervieweeName(String intervieweeName) {
    this.intervieweeName = intervieweeName;
  }

  public int getSexId() {
    return sexId;
  }

  public void setSexId(int sexId) {
    this.sexId = sexId;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
    this.age = TimeUtil.countAges(birthday);
  }
}
