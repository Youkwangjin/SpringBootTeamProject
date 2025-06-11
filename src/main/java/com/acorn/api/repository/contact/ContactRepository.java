package com.acorn.api.repository.contact;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.contact.Contact;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContactRepository {

    Integer selectContactIdKey();

    Integer selectListCountByUserRequest(PaginationRequest paginationRequest);

    List<Contact> selectContactUserListData(PaginationRequest paginationRequest);

    void saveContact(Contact contact);
}