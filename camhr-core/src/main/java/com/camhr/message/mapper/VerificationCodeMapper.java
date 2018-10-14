package com.camhr.message.mapper;

import com.camhr.message.entity.VerificationCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VerificationCodeMapper {

  int addVerificationCode(VerificationCode verificationCode);

  int updateVerificationCode(VerificationCode verificationCode);

  VerificationCode getVerificationCode(@Param("principal") String principal);
}
