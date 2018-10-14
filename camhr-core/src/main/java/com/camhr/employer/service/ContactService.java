package com.camhr.employer.service;

import com.camhr.employer.entity.Contact;
import java.util.List;

/**
 * Created by Allen on 2018/9/18.
 */
public interface ContactService {

  Contact getContactBaseInfo(Long contactId);

  // TODO 缓存注解
  Contact getContact(Long contactId);

  int addContact(Contact contact);

  List<Contact> getContactsByEmployer(long employerId);
}
