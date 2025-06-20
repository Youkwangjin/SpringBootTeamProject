package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.request.AdminContactReviewReqDTO;
import com.acorn.api.dto.admin.request.AdminContactAnswerReqDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.contact.response.ContactDetailResDTO;
import com.acorn.api.dto.contact.response.ContactFileDownloadResDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminContactService {
    List<ContactListResDTO> getContactListData(CommonListReqDTO listData);

    ContactDetailResDTO selectContactDetailData(Integer contactId);

    ContactFileDownloadResDTO contactAdminFileDownload(Integer contactId, Integer contactFileId);

    void processReviewRequest(AdminContactReviewReqDTO requsetData);

    void processAnswerRequest(AdminContactAnswerReqDTO requsetData);
}