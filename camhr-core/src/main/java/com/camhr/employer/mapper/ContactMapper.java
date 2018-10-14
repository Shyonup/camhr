package com.camhr.employer.mapper;

import com.camhr.employer.entity.Contact;
import com.camhr.employer.entity.Employer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContactMapper {

  Contact getContact(@Param("contactId") long contactId);

  int addContact(Contact contact);

  List<Contact> getContactsByEmployer(@Param("employerId") long employerId);
}
