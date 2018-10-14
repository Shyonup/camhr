package com.camhr.employer.service.impl;

import com.camhr.employer.entity.Contact;
import com.camhr.employer.mapper.ContactMapper;
import com.camhr.employer.service.ContactService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2018/9/18.
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactMapper contactMapper;

  @Override
  public Contact getContactBaseInfo(Long contactId) {
    Contact contact = getContact(contactId);
    if (contact != null) {
      contact.setEmail(""); // 不返回邮箱
      contact.setTelephone(""); // 联系电话也不返回
      contact.setDepartmentId(0); // 部门不返回
    }
    return contact;
  }

  @Override
  public Contact getContact(Long contactId) {
    if (contactId == null) {
      contactId = 0L;
    }
    return contactMapper.getContact(contactId);
  }

  @Override
  public int addContact(Contact contact) {
    return contactMapper.addContact(contact);
  }

  @Override
  public List<Contact> getContactsByEmployer(long employerId) {
    return contactMapper.getContactsByEmployer(employerId);
  }
}
