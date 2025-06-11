package com.acorn.api.repository.contact;

import com.acorn.api.entity.contact.ContactFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContactFileRepository {

    Integer selectContactFileIdKey();

    void saveContactFile(ContactFile contactFile);
}