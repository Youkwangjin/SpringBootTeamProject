package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.request.ContactManagementReqDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.contact.response.ContactDetailResDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminContactService {
    List<ContactListResDTO> getContactListData(CommonListReqDTO listData);

    ContactDetailResDTO selectContactDetailData(Integer contactId);

    void processReviewRequest(ContactManagementReqDTO requsetData);
}