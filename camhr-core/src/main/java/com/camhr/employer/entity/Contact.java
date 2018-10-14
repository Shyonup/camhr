package com.camhr.employer.entity;

import we.json.AsConstant;

/**
 * 公司联系人
 *
 * Created by Allen on 2018/9/18.
 */
public class Contact {

  private int id;

  private int employerId;

  private String name;

  private int departmentId;

  @AsConstant("POSITION")
  private int positionId;

  private String telephone;

  private String email;

  private String fax;

  private boolean emailCertification; // '0-未认证，1-已认证'

  private boolean isdelete; // '0-正常，1-删除'

  private boolean emailBlock; // '0-正常，1-屏蔽'

  // ==================================== get / set ====================================

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getEmployerId() {
    return employerId;
  }

  public void setEmployerId(int employerId) {
    this.employerId = employerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public boolean isEmailCertification() {
    return emailCertification;
  }

  public void setEmailCertification(boolean emailCertification) {
    this.emailCertification = emailCertification;
  }

  public boolean isIsdelete() {
    return isdelete;
  }

  public void setIsdelete(boolean isdelete) {
    this.isdelete = isdelete;
  }

  public boolean isEmailBlock() {
    return emailBlock;
  }

  public void setEmailBlock(boolean emailBlock) {
    this.emailBlock = emailBlock;
  }
}
