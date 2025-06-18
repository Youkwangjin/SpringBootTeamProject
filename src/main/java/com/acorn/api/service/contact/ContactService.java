package com.acorn.api.service.contact;

import com.acorn.api.dto.contact.request.ContactListReqDTO;
import com.acorn.api.dto.contact.request.ContactCancelReqDTO;
import com.acorn.api.dto.contact.request.ContactDeleteReqDTO;
import com.acorn.api.dto.contact.request.ContactSaveReqDTO;
import com.acorn.api.dto.contact.request.ContactUpdateReqDTO;
import com.acorn.api.dto.contact.response.ContactDetailResDTO;
import com.acorn.api.dto.contact.response.ContactFileDownloadResDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {

    List<ContactListResDTO> getUserContactList(ContactListReqDTO listData);

    ContactDetailResDTO selectContactDetailData(Integer contactId);

    void contactSaveData(ContactSaveReqDTO saveData);

    void contactDataUpdate(ContactUpdateReqDTO updateData);

    void contactDataDelete(ContactDeleteReqDTO deleteData);

    void contactDataCancel(ContactCancelReqDTO cancelData);

    ContactFileDownloadResDTO contactFileDownload(Integer contactId, Integer contactFileId);
}