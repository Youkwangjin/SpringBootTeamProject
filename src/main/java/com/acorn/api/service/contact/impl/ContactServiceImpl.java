package com.acorn.api.service.contact.impl;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.contact.ContactListDTO;
import com.acorn.api.entity.contact.Contact;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.contact.ContactRepository;
import com.acorn.api.service.contact.ContactService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<ContactListDTO> getUserContactList(ContactListDTO listData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        listData.setContactUserId(currentUserId);
        listData.setTotalCount(contactRepository.selectListCountByUserRequest(listData));
        List<Contact> contactListData = contactRepository.selectContactUserListData(listData);
        return contactListData.stream()
                .map(contactList -> {
                    final Integer rowNum = contactList.getRowNum();
                    final Integer contactId = contactList.getContactId();
                    final String contactTitle = contactList.getContactTitle();
                    final Integer contactStatus = contactList.getContactStatus();
                    final String contactAnswerYn = contactList.getContactAnswerYn();
                    final LocalDateTime contactCreated = contactList.getContactCreated();

                    return ContactListDTO.builder()
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