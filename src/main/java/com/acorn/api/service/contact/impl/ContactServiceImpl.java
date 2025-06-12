package com.acorn.api.service.contact.impl;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.contact.ContactStaus;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.contact.ContactListDTO;
import com.acorn.api.dto.contact.ContactSaveDTO;
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
import java.util.List;
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
    public List<ContactListDTO> getUserContactList(ContactListDTO listData) {
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

    @Override
    @Transactional
    public void contactSaveData(ContactSaveDTO saveData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer contactWriterId;
        final Integer contactWriterType;

        if (currentUserId != null) {
            contactWriterId = currentUserId;
            contactWriterType = ContactStaus.USER.getCode();
        } else if (currentOwnerId != null) {
            contactWriterId = currentOwnerId;
            contactWriterType = ContactStaus.OWNER.getCode();
        } else {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        final Integer contactId = contactRepository.selectContactIdKey();
        final String contactTitle = saveData.getContactTitle();
        final String contactContents = saveData.getContactContents();
        final String contactContentsText = Jsoup.parse(contactContents).text();
        final Integer contactStatus = ContactStaus.CONTACT_STATUS_PENDING.getCode();
        final String contactAnswerYn = "N";
        final List<MultipartFile> contactFiles = saveData.getContactFiles();

        Contact saveContactData = Contact.builder()
                .contactId(contactId)
                .contactUserId(contactWriterId)
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
}