package com.acorn.api.service.contact;

import com.acorn.api.dto.contact.*;
import com.acorn.api.dto.contact.requset.ContactCancelReqDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {

    List<ContactListDTO> getUserContactList(ContactListDTO listData);

    ContactDetailDTO selectContactDetailData(Integer contactId);

    void contactSaveData(ContactSaveDTO saveData);

    void contactDataUpdate(ContactUpdateDTO updateData);

    void contactDataDelete(ContactDeleteDTO deleteData);

    void contactDataCancel(ContactCancelReqDTO cancelData);
}