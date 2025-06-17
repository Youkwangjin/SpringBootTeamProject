package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.contact.response.ContactDetailResDTO;
import com.acorn.api.dto.contact.response.ContactFileResDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import com.acorn.api.entity.contact.Contact;
import com.acorn.api.entity.contact.ContactFile;
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

    @Override
    public ContactDetailResDTO selectContactDetailData(Integer contactId) {
        Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final String contactTitle = detailData.getContactTitle();
        final String contactContents = detailData.getContactContents();
        final String contactContentsText = detailData.getContactContentsText();
        final Integer contactStatus = detailData.getContactStatus();
        final String contactAdminContents = detailData.getContactAdminContents();
        final String contactAnswerYn = detailData.getContactAnswerYn();
        final LocalDateTime contactCreated = detailData.getContactCreated();

        List<ContactFile> contactFileEntities = detailData.getContactFilesList();
        final List<ContactFileResDTO> contactFileData = contactFileEntities.stream()
                .map(contactFile -> {
                    final Integer contactFileId = contactFile.getContactFileId();
                    final String contactOriginalFileName = contactFile.getContactOriginalFileName();
                    final String contactStoredFileName = contactFile.getContactStoredFileName();
                    final String contactFilePath = contactFile.getContactFilePath();
                    final String contactFileExtNm = contactFile.getContactFileExtNm();
                    final String contactFileSize = contactFile.getContactFileSize();

                    return ContactFileResDTO.builder()
                            .contactFileId(contactFileId)
                            .contactOriginalFileName(contactOriginalFileName)
                            .contactStoredFileName(contactStoredFileName)
                            .contactFilePath(contactFilePath)
                            .contactFileExtNm(contactFileExtNm)
                            .contactFileSize(contactFileSize)
                            .build();

                })
                .collect(Collectors.toList());

        return ContactDetailResDTO.builder()
                .contactId(contactId)
                .contactTitle(contactTitle)
                .contactContents(contactContents)
                .contactContentsText(contactContentsText)
                .contactStatus(contactStatus)
                .contactAdminContents(contactAdminContents)
                .contactAnswerYn(contactAnswerYn)
                .contactCreated(contactCreated)
                .contactFiles(contactFileData)
                .build();
    }
}