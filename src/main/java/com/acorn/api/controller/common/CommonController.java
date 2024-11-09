package com.acorn.api.controller.common;

import com.acorn.api.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    private final CommonService commonService;

    @PostMapping("/api/editor/image/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {

        log.info(" *****************************    uploadImage START    *****************************");

        Map<String, Object> responseData = new HashMap<>();
        try {
            String data = commonService.imageUpload(file);
            responseData.put("uploaded", true);
            responseData.put("data", data);
            return ResponseEntity.ok(responseData);

        } catch (IOException e) {
            responseData.put("uploaded", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }
}
