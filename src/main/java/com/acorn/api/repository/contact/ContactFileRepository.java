package com.acorn.api.repository.contact;

import com.acorn.api.entity.contact.ContactFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContactFileRepository {

    Integer selectContactFileIdKey();

    void saveContactFile(ContactFile contactFile);

    List<ContactFile> selectFilesByContactId(@Param("contactId") Integer contactId);

    ContactFile selectFilesByContactFileId(@Param("contactFileId") Integer contactFileId);

    void deleteContactFile(@Param("contactFileId") Integer contactFileId);
}