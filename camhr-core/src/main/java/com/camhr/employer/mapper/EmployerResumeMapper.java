package com.camhr.employer.mapper;

import com.camhr.employer.builder.ResumeQueryBuilder;
import com.camhr.employer.entity.ResumeInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployerResumeMapper {

  List<ResumeInfo> queryResumeInfoList(ResumeQueryBuilder resumeQueryBuilder);

  long countResumeInfoList(ResumeQueryBuilder resumeQueryBuilder);

  int addEemployerHits(long resumeId);
}
