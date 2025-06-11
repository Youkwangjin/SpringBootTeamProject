package com.acorn.api.service.contact;

import com.acorn.api.dto.contact.ContactListDTO;
import com.acorn.api.dto.contact.ContactSaveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {

    List<ContactListDTO> getUserContactList(ContactListDTO listData);

    void contactSaveData(ContactSaveDTO saveData);
}