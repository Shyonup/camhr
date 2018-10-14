package com.camhr.user.mapper;

import com.camhr.resume.entity.ReqLocation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReqLocationMapper {

  int  addReqLocation(@Param("reqLocations") List<ReqLocation> reqLocations);

  int  deleteReqLocationByResumeId(long resumeId);

  List<Integer> getReqLocationIds(long resumeId);
}
