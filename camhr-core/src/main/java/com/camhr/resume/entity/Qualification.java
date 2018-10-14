package com.camhr.resume.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import we.web.json.OSSPath;

public class Qualification {

  @ApiModelProperty(hidden = true)
  private long id;
  @ApiModelProperty(notes = "简历id")
  @Min(1)
  private long resumeId;
  @ApiModelProperty(notes = "证书名称")
  @NotBlank
  @Length(max = 255)
  private String name;
  @ApiModelProperty(notes = "发证单位")
  @NotBlank
  @Length(max = 255)
  private String issued;
  @ApiModelProperty(notes = "发证年")
  @Min(1)
  private int obtained;

  private String photo;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getResumeId() {
    return resumeId;
  }

  public void setResumeId(long resumeId) {
    this.resumeId = resumeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIssued() {
    return issued;
  }

  public void setIssued(String issued) {
    this.issued = issued;
  }

  public int getObtained() {
    return obtained;
  }

  public void setObtained(int obtained) {
    this.obtained = obtained;
  }

  @ApiModelProperty(notes = "文件路径")
  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }
}
