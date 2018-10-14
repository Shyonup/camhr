package com.camhr.user.error;

import org.springframework.http.HttpStatus;
import we.lang.ErrorCode;
import we.lang.GlobalErrorCodes;

public class UserErrorCodes {

  public final static ErrorCode USERNAME_EXIST = GlobalErrorCodes
      .addCode(200, 1, HttpStatus.BAD_REQUEST.value(),
          "${user.username_exist:用户名已存在}");

  public final static ErrorCode EMAIL_EXIST = GlobalErrorCodes
      .addCode(200, 2, HttpStatus.BAD_REQUEST.value(),
          "${user.email_exist:EMAIL已存在}");

  public final static ErrorCode REGISTER_FAIL = GlobalErrorCodes
      .addCode(200, 3, HttpStatus.BAD_REQUEST.value(),
          "${user.register_fail:用户注册失败}");

  public final static ErrorCode MOBILE_NOT_EXIST = GlobalErrorCodes
      .addCode(200, 4, HttpStatus.BAD_REQUEST.value(),
          "${user.mobile_not_exist:不存在的手机号}");

  public final static ErrorCode INVALID_VERIFY_CODE = GlobalErrorCodes
      .addCode(200, 5, HttpStatus.BAD_REQUEST.value(),
          "${user.invalid_verify_code:无效的验证码}");

  public final static ErrorCode MOBILE_EXISTED = GlobalErrorCodes
      .addCode(200, 6, HttpStatus.BAD_REQUEST.value(),
          "${user.mobile_existed:已存在的手机号}");

  public final static ErrorCode EMAIL_NOT_EXIST = GlobalErrorCodes
      .addCode(200, 7, HttpStatus.BAD_REQUEST.value(),
          "${user.email_not_exist:EMAIL不存在}");
}
