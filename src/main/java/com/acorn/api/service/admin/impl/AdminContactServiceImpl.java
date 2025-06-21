package com.acorn.api.service.admin.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.contact.ContactStatus;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.admin.request.AdminContactReviewReqDTO;
import com.acorn.api.dto.admin.request.AdminContactAnswerReqDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.contact.response.ContactDetailResDTO;
import com.acorn.api.dto.contact.response.ContactFileDownloadResDTO;
import com.acorn.api.dto.contact.response.ContactFileResDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import com.acorn.api.entity.contact.Contact;
import com.acorn.api.entity.contact.ContactFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.contact.ContactFileRepository;
import com.acorn.api.repository.contact.ContactRepository;
import com.acorn.api.service.admin.AdminContactService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminContactServiceImpl implements AdminContactService {

    private final ContactRepository contactRepository;
    private final ContactFileRepository contactFileRepository;
    private final FileComponent fileComponent;

    @Override
    public List<ContactListResDTO> getContactListData(CommonListReqDTO listData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
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
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
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
                            .contactId(contactId)
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

    @Override
    public ContactFileDownloadResDTO contactAdminFileDownload(Integer contactId, Integer contactFileId) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        final ContactFile detailData = contactFileRepository.selectContactFile(contactId, contactFileId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FILE_DATA);
        }

        final String originalFileName = detailData.getContactOriginalFileName();
        final String storedFileName = detailData.getContactStoredFileName();
        final String filePath = detailData.getContactFilePath();

        byte[] fileBytes = fileComponent.download(filePath, storedFileName);

        return ContactFileDownloadResDTO.builder()
                .originalFileName(originalFileName)
                .fileBytes(fileBytes)
                .build();
    }

    @Override
    @Transactional
    public void processReviewRequest(AdminContactReviewReqDTO requsetData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer contactId = requsetData.getContactId();
        final Integer contactPendingStatus = ContactStatus.CONTACT_STATUS_PENDING.getCode();
        final Integer contactProgressStatus = ContactStatus.CONTACT_STATUS_PROGRESS.getCode();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final Integer contactStatus = detailData.getContactStatus();
        if (!Objects.equals(contactStatus, contactPendingStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTACT_STATUS_NOT_PENDING);
        }

        Contact updateStatus = Contact.builder()
                .contactId(contactId)
                .contactStatus(contactProgressStatus)
                .build();

        contactRepository.updateContactStatus(updateStatus);
    }

    @Override
    @Transactional
    public void processAnswerRequest(AdminContactAnswerReqDTO requsetData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer contactId = requsetData.getContactId();
        final String contactAdminContents = requsetData.getContactAdminContents();
        final String contactAnswerYn = "Y";
        final Integer contactProgressStatus = ContactStatus.CONTACT_STATUS_PROGRESS.getCode();
        final Integer contactCompletedStatus = ContactStatus.CONTACT_STATUS_COMPLETED.getCode();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final Integer contactStatus = detailData.getContactStatus();
        if (!Objects.equals(contactStatus, contactProgressStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTACT_STATUS_NOT_PROCESSING);
        }

        Contact updateContactData = Contact.builder()
                .contactId(contactId)
                .contactAdminId(currentAdminId)
                .contactStatus(contactCompletedStatus)
                .contactAdminContents(contactAdminContents)
                .contactAnswerYn(contactAnswerYn)
                .build();

        contactRepository.updateAdminContact(updateContactData);
    }
}