package com.camhr.job.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import we.json.AsConstant;

/**
 * Created by Allen on 2018/9/17.
 */
public class JobLocation {

  @JsonIgnore
  private int id;

  @JsonIgnore
  private long jobId;

  @AsConstant("LOCATION")
  private int locationId;

  @Length(max = 256)
  private String address;

  // ==================================== 普通方法 ====================================
  public static JobLocation of(long jobId, int locationId) {
    JobLocation jobLocation = new JobLocation();
    jobLocation.setJobId(jobId);
    jobLocation.setLocationId(locationId);
    return jobLocation;
  }

  // ==================================== get / set ====================================

  @ApiModelProperty(hidden = true)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ApiModelProperty(hidden = true)
  public long getJobId() {
    return jobId;
  }

  public void setJobId(long jobId) {
    this.jobId = jobId;
  }

  public int getLocationId() {
    return locationId;
  }

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
