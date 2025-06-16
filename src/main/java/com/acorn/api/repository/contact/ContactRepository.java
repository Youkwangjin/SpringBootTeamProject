package com.acorn.api.repository.contact;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.contact.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContactRepository {

    Integer selectContactIdKey();

    Integer selectListCountByRequest(PaginationRequest paginationRequest);

    Integer selectAdminListCountByRequest(PaginationRequest paginationRequest);

    List<Contact> selectContactListData(PaginationRequest paginationRequest);

    List<Contact> selectAdminContactListData(PaginationRequest paginationRequest);

    Contact selectContactDetailData(@Param("contactId") Integer contactId);

    void saveContact(Contact contact);

    void updateContact(Contact contact);

    void deleteContact(Contact contact);

    void updateContactStatus(Contact contact);
}