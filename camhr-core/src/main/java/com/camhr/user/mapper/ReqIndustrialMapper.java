package com.camhr.user.mapper;

import com.camhr.resume.entity.ReqIndustrial;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ReqIndustrialMapper {

  int  addIndustrial(@Param("industryIds") List<ReqIndustrial> industryIds);

  int  deleteIndustrialByResumeId(long resumeId);

  List<Integer> getReqIndustrial(long resumeId);
}
