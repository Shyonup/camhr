package com.camhr.im.mapper;

import com.camhr.im.entity.IMAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMAccountMapper {

  int addAccount(IMAccount imAccount);

  boolean existAccount(IMAccount imAccount);

  IMAccount getIMAccount(@Param("account") String account, @Param("accountType") int accountType);
}
