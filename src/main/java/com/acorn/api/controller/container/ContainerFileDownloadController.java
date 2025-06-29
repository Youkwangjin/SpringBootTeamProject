package com.acorn.api.controller.container;

import com.acorn.api.dto.container.response.ContainerFileDownloadResDTO;
import com.acorn.api.service.container.ContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class ContainerFileDownloadController {

    private final ContainerService containerService;

    @GetMapping("/api/container/file/download/{containerId}/{containerFileId}")
    public ResponseEntity<byte[]> containerFileDownload(@PathVariable("containerId") Integer containerId, @PathVariable("containerFileId") Integer containerFileId) {
        ContainerFileDownloadResDTO resData = containerService.containerFileDownload(containerId, containerFileId);

        String fileName = UriUtils.encode(resData.getOriginalFileName(), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return new ResponseEntity<>(resData.getFileBytes(), headers, HttpStatus.OK);
    }
}