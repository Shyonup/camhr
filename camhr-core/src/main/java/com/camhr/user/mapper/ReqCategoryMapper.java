package com.camhr.user.mapper;

import com.camhr.resume.entity.ReqCategory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReqCategoryMapper {

  int  addCategory(@Param("reqCategories") List<ReqCategory> reqCategories);

  int  deleteCategoryByResumeId(long resumeId);

  List<Integer> getReqCategoryIdsByResumeId(long resumeId);
}
