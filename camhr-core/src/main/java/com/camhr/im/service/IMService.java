package com.camhr.im.service;

import com.camhr.im.entity.IMAccount;

public interface IMService {

  int createIMAccount(IMAccount imAccount);

  boolean updateUserInfo(IMAccount imAccount);

  boolean existIMAccount(IMAccount imAccount);

  IMAccount getIMAccount(String name, int accountType);
}
