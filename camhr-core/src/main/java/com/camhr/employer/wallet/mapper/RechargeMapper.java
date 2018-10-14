package com.camhr.employer.wallet.mapper;

import com.camhr.employer.wallet.builders.RechargeQueryBuider;
import com.camhr.employer.wallet.entity.Recharge;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RechargeMapper {

  int addRecharge(Recharge recharge);

  Recharge getRecharge(@Param("orderId") long orderId);

  int updateRechargeStatus(Recharge recharge);

  long countRecharge(RechargeQueryBuider rechargeQueryBuider);

  List<Recharge> queryRecharge(RechargeQueryBuider rechargeQueryBuider);
}
