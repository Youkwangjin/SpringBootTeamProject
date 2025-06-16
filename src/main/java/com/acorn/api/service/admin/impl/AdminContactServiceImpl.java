package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import com.acorn.api.entity.contact.Contact;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.contact.ContactRepository;
import com.acorn.api.service.admin.AdminContactService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminContactServiceImpl implements AdminContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<ContactListResDTO> getContactListData(CommonListReqDTO listData) {
        Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        listData.setTotalCount(contactRepository.selectAdminListCountByRequest(listData));
        List<Contact> contactListData = contactRepository.selectAdminContactListData(listData);
        return contactListData.stream()
                .map(contactList -> {
                    final Integer rowNum = contactList.getRowNum();
                    final Integer contactId = contactList.getContactId();
                    final String contactTitle = contactList.getContactTitle();
                    final Integer contactStatus = contactList.getContactStatus();
                    final String contactAnswerYn = contactList.getContactAnswerYn();
                    final LocalDateTime contactCreated = contactList.getContactCreated();

                    return ContactListResDTO.builder()
                            .rowNum(rowNum)
                            .contactId(contactId)
                            .contactTitle(contactTitle)
                            .contactStatus(contactStatus)
                            .contactAnswerYn(contactAnswerYn)
                            .contactCreated(contactCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }
}