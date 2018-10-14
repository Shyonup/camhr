package com.camhr.employer.wallet.controller;


import com.camhr.CamhrProperties;
import com.camhr.constants.AccountType;
import com.camhr.employer.wallet.builders.RechargeQueryBuider;
import com.camhr.employer.wallet.entity.OrderPaymentRequest;
import com.camhr.employer.wallet.entity.Recharge;
import com.camhr.employer.wallet.service.RechargeService;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import we.lang.Exceptions;
import we.payment.PaymentResponse;
import we.payment.events.NotifyEvent;
import we.payment.mpay.MPayProperties;
import we.payment.mpay.PaymentStatusMapping;
import we.payment.mpay.utils.SignUtils;
import we.security.annotation.CurrentUser;
import we.security.rbac.SimpleUser;
import we.web.Result;

@RestController
@RequestMapping("/${version:v1.0.0}/employers/wallets/recharges")
public class RechargeController {

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Autowired
  private CamhrProperties camhrProperties;

  @Autowired
  private RechargeService rechargeService;

  @Autowired
  private MPayProperties mPayProperties;

  @ApiOperation(value = "充值订单查询", response = Recharge.class)
  @GetMapping()
  public Result queryRecharges(@ApiIgnore @CurrentUser SimpleUser simpleUser,
      RechargeQueryBuider rechargeQueryBuider) {
    rechargeQueryBuider.setEmployerId(simpleUser.getUserId());
    return Result.data(rechargeService.queryRecharges(rechargeQueryBuider));
  }

  @PostMapping()
  @ApiOperation(value = "账户充值订单（积分/金币）", notes = "账户充值订单（积分/金币）")
  public Result recharge(@ApiIgnore @CurrentUser SimpleUser simpleUser,
      @RequestParam BigDecimal amount,
      @RequestParam @Min(1) int paymethod) {
    if (amount.compareTo(camhrProperties.getMinRechargeAmount()) < 0) {
      Exceptions.of(RechargeController.class)
          .error(HttpStatus.BAD_REQUEST.value(),
              "Minimum recharge amount:" + camhrProperties.getMinRechargeAmount());
    }
    Recharge recharge = new Recharge();
    recharge.setEmployerId((int) simpleUser.getUserId());
    recharge.setCash(amount);
    recharge.setCashrep(amount);
    return Result.of(rechargeService.addRecharge(recharge))
        .addAttribute("orderId", recharge.getId())
        .fail("${employer.recharge.order.add.fail:fail}")
        .success("${employer.recharge.order.add.success:success}");
  }

  @PostMapping("/{orderId}/pay")
  @ApiOperation(value = "充值支付", notes = "充值支付")
  public Result payRecharge(@ApiIgnore @CurrentUser SimpleUser simpleUser,
      @PathVariable("orderId") @Min(1) long orderId,
      @Valid @RequestBody OrderPaymentRequest orderPaymentRequest) {
    orderPaymentRequest.setOrderId(orderId);
    orderPaymentRequest.setUserId(simpleUser.getUserId());
    orderPaymentRequest.setAccountType(AccountType.EMPLOYER.value());
    PaymentResponse response = rechargeService.payRecharge(orderPaymentRequest);
    return Result.of(response.isSuccessful())
        .addAttribute(Result.DATA, response).fail(response.getFailureReason());
  }


  //异步回调 post body json
  @PostMapping("/callback")
  public String notify(@RequestBody Map<String, String> params) {
    JSONObject result = new JSONObject();
    if (SignUtils.verify(params, mPayProperties.getSignKey())) {//验证成功
      PaymentResponse paymentResponse = new PaymentResponse();
      paymentResponse.setResult(new JSONObject(params).toMap());
      paymentResponse.setPaymentId(params.get("outOrderNo"));
      paymentResponse.setStatus(
          PaymentStatusMapping.of(params.get("tradeState")).getPaymentStatus());
      this.eventPublisher.publishEvent(NotifyEvent.of(paymentResponse));
      result.put("msgCd", "SUCCESS");
    } else {
      result.put("msgCd", "FAIL");
    }
    return result.toString();
  }
}
