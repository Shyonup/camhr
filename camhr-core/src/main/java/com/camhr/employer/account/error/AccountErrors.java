package com.camhr.employer.account.error;

import com.camhr.constants.ModuleConstants;
import org.springframework.http.HttpStatus;
import we.lang.ErrorCode;
import we.lang.GlobalErrorCodes;

public class AccountErrors {

  public final static ErrorCode INSUFFICIENT_BALANCE = GlobalErrorCodes
      .addCode(ModuleConstants.ACCOUNT, 1, HttpStatus.BAD_REQUEST.value(),
          "${employer.account.insufficient_balance:Insufficient balance}");
}
