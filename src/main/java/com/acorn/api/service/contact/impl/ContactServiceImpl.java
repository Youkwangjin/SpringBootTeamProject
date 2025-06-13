package com.acorn.api.service.contact.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.contact.ContactStatus;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.contact.request.ContactListReqDTO;
import com.acorn.api.dto.contact.request.ContactCancelReqDTO;
import com.acorn.api.dto.contact.request.ContactDeleteReqDTO;
import com.acorn.api.dto.contact.request.ContactSaveReqDTO;
import com.acorn.api.dto.contact.request.ContactUpdateReqDTO;
import com.acorn.api.dto.contact.response.ContactDetailResDTO;
import com.acorn.api.dto.contact.response.ContactFileResDTO;
import com.acorn.api.dto.contact.response.ContactListResDTO;
import com.acorn.api.entity.contact.Contact;
import com.acorn.api.entity.contact.ContactFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.contact.ContactFileRepository;
import com.acorn.api.repository.contact.ContactRepository;
import com.acorn.api.service.contact.ContactService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactFileRepository contactFileRepository;
    private final FileComponent fileComponent;

    @Value("${file.upload.path.contact}")
    private String uploadDir;

    @Override
    public List<ContactListResDTO> getUserContactList(ContactListReqDTO listData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();

        if (currentUserId != null) {
            listData.setContactUserId(currentUserId);
        } else if (currentOwnerId != null) {
            listData.setContactOwnerId(currentOwnerId);
        } else {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        listData.setTotalCount(contactRepository.selectListCountByRequest(listData));
        List<Contact> contactListData = contactRepository.selectContactListData(listData);
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
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();

        if (currentUserId == null && currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final Integer contactUserId = detailData.getContactUserId();
        final Integer contactOwnerId = detailData.getContactOwnerId();
        final String contactTitle = detailData.getContactTitle();
        final String contactContents = detailData.getContactContents();
        final String contactContentsText = detailData.getContactContentsText();
        final Integer contactStatus = detailData.getContactStatus();
        final String contactAdminContents = detailData.getContactAdminContents();
        final String contactAnswerYn = detailData.getContactAnswerYn();
        final LocalDateTime contactCreated = detailData.getContactCreated();

        if (currentUserId != null) {
            if (!Objects.equals(currentUserId, contactUserId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }

        } else {
            if (!Objects.equals(currentOwnerId, contactOwnerId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        }

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
                .contactUserId(contactUserId)
                .contactOwnerId(contactOwnerId)
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
    @Transactional
    public void contactSaveData(ContactSaveReqDTO saveData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer contactWriterType;

        if (currentUserId != null) {
            contactWriterType = ContactStatus.USER.getCode();

        } else if (currentOwnerId != null) {
            contactWriterType = ContactStatus.OWNER.getCode();

        } else {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        final Integer contactId = contactRepository.selectContactIdKey();
        final String contactTitle = saveData.getContactTitle();
        final String contactContents = saveData.getContactContents();
        final String contactContentsText = Jsoup.parse(contactContents).text();
        final Integer contactStatus = ContactStatus.CONTACT_STATUS_PENDING.getCode();
        final String contactAnswerYn = "N";
        final List<MultipartFile> contactFiles = saveData.getContactFiles();

        Contact saveContactData = Contact.builder()
                .contactId(contactId)
                .contactUserId(currentUserId)
                .contactOwnerId(currentOwnerId)
                .contactTitle(contactTitle)
                .contactContents(contactContents)
                .contactContentsText(contactContentsText)
                .contactStatus(contactStatus)
                .contactWriterType(contactWriterType)
                .contactAnswerYn(contactAnswerYn)
                .build();

        contactRepository.saveContact(saveContactData);

        if (contactFiles != null && !contactFiles.isEmpty()) {
            for (MultipartFile multipartFile : contactFiles) {
                final Integer contactFileId = contactFileRepository.selectContactFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", contactId, contactFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                ContactFile saveContactFileData = ContactFile.builder()
                        .contactFileId(contactFileId)
                        .contactId(contactId)
                        .contactOriginalFileName(originalFileName)
                        .contactStoredFileName(storedFileName)
                        .contactFilePath(filePath)
                        .contactFileExtNm(fileExtNm)
                        .contactFileSize(fileSize)
                        .build();

                contactFileRepository.saveContactFile(saveContactFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    @Transactional
    public void contactDataUpdate(ContactUpdateReqDTO updateData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer contactId = updateData.getContactId();
        final String contactTitle = updateData.getContactTitle();
        final String contactContents = updateData.getContactContents();
        final String contactContentsText = Jsoup.parse(contactContents).text();
        final List<MultipartFile> contactFiles = updateData.getContactFiles();
        final Integer contactPendingStatus = ContactStatus.CONTACT_STATUS_PENDING.getCode();

        if (currentUserId == null && currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final Integer existingUserId = detailData.getContactUserId();
        final Integer existingOwnerId = detailData.getContactOwnerId();
        final Integer existingContactStatus = detailData.getContactStatus();

        if (currentUserId != null) {
            if (!Objects.equals(existingUserId, currentUserId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        } else {
            if (!Objects.equals(existingOwnerId, currentOwnerId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        }

        if (!Objects.equals(contactPendingStatus, existingContactStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_WAITING);
        }

        Contact updateContactData = Contact.builder()
                .contactId(contactId)
                .contactTitle(contactTitle)
                .contactContents(contactContents)
                .contactContentsText(contactContentsText)
                .build();

        contactRepository.updateContact(updateContactData);

        List<Integer> contactFileIds = updateData.getContactFileIds();
        if (contactFileIds == null) {
            contactFileIds = new ArrayList<>();
        }

        List<ContactFile> existingFiles = contactFileRepository.selectFilesByContactId(contactId);
        List<Integer> deleteFileIds = new ArrayList<>();
        for (ContactFile contactFile : existingFiles) {
            if (!contactFileIds.contains(contactFile.getContactFileId())) {
                deleteFileIds.add(contactFile.getContactFileId());
            }
        }

        for (Integer contactFileId : deleteFileIds) {
            ContactFile contactFile = contactFileRepository.selectFilesByContactFileId(contactFileId);
            if (contactFile == null) {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }
            final String filePath = contactFile.getContactFilePath();
            final String storedFileName = contactFile.getContactStoredFileName();

            fileComponent.delete(filePath, storedFileName);
            contactFileRepository.deleteContactFile(contactFileId);
        }

        if (contactFiles != null && !contactFiles.isEmpty()) {
            for (MultipartFile multipartFile : contactFiles) {
                final Integer contactFileId = contactFileRepository.selectContactFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", contactId, contactFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                ContactFile saveContactFileData = ContactFile.builder()
                        .contactFileId(contactFileId)
                        .contactId(contactId)
                        .contactOriginalFileName(originalFileName)
                        .contactStoredFileName(storedFileName)
                        .contactFilePath(filePath)
                        .contactFileExtNm(fileExtNm)
                        .contactFileSize(fileSize)
                        .build();

                contactFileRepository.saveContactFile(saveContactFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    public void contactDataDelete(ContactDeleteReqDTO deleteData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer contactId = deleteData.getContactId();
        final Integer contactPendingStatus = ContactStatus.CONTACT_STATUS_PENDING.getCode();

        if (currentUserId == null && currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final Integer existingUserId = detailData.getContactUserId();
        final Integer existingOwnerId = detailData.getContactOwnerId();
        final Integer existingContactStatus = detailData.getContactStatus();

        if (currentUserId != null) {
            if (!Objects.equals(existingUserId, currentUserId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        } else {
            if (!Objects.equals(existingOwnerId, currentOwnerId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        }

        if (!Objects.equals(contactPendingStatus, existingContactStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_WAITING);
        }

        List<ContactFile> existingFiles = contactFileRepository.selectFilesByContactId(contactId);
        if (existingFiles != null && !existingFiles.isEmpty()) {
            for (ContactFile contactFile : existingFiles) {
                final Integer contactFileId = contactFile.getContactFileId();
                final String filePath = contactFile.getContactFilePath();
                final String storedFileName = contactFile.getContactStoredFileName();

                fileComponent.delete(filePath, storedFileName);
                contactFileRepository.deleteContactFile(contactFileId);
            }
        }

        Contact deleteContactData = Contact.builder()
                .contactId(contactId)
                .build();

        contactRepository.deleteContact(deleteContactData);
    }

    @Override
    @Transactional
    public void contactDataCancel(ContactCancelReqDTO cancelData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer contactId = cancelData.getContactId();
        final Integer contactPendingStatus = ContactStatus.CONTACT_STATUS_PENDING.getCode();
        final Integer contactCancelStatus = ContactStatus.CONTACT_STATUS_CANCELLED.getCode();

        if (currentUserId == null && currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Contact detailData = contactRepository.selectContactDetailData(contactId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_FOUND);
        }

        final Integer existingUserId = detailData.getContactUserId();
        final Integer existingOwnerId = detailData.getContactOwnerId();
        final Integer existingContactStatus = detailData.getContactStatus();

        if (currentUserId != null) {
            if (!Objects.equals(existingUserId, currentUserId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        } else {
            if (!Objects.equals(existingOwnerId, currentOwnerId)) {
                throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
            }
        }

        if (!Objects.equals(contactPendingStatus, existingContactStatus)) {
            throw new AcontainerException(ApiErrorCode.CONTACT_NOT_WAITING);
        }

        Contact cancelContactData = Contact.builder()
                .contactId(contactId)
                .contactStatus(contactCancelStatus)
                .build();

        contactRepository.updateContactStatus(cancelContactData);
    }
}