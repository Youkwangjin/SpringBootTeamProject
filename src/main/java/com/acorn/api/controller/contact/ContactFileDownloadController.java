package com.acorn.api.controller.contact;

import com.acorn.api.dto.contact.response.ContactFileDownloadResDTO;
import com.acorn.api.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactFileDownloadController {

    private final ContactService contactService;

    @GetMapping("/api/contact/file/download/{contactId}/{contactFileId}")
    public ResponseEntity<byte[]> contactFileDownload(@PathVariable("contactId") Integer contactId, @PathVariable("contactFileId") Integer contactFileId) {
        log.info(" *****************************    Contact File  Download START    *****************************");

        ContactFileDownloadResDTO resData = contactService.contactFileDownload(contactId, contactFileId);

        String fileName = UriUtils.encode(resData.getOriginalFileName(), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resData.getFileBytes(), headers, HttpStatus.OK);
    }
}