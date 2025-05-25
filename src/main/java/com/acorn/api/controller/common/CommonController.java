package com.acorn.api.controller.common;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.container.ContainerMapListDTO;
import com.acorn.api.service.common.CommonService;
import com.acorn.api.service.container.ContainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;
    private final ContainerService containerService;

    @GetMapping("/api/geocode")
    public ResponseEntity<?> getCoordinates(@RequestParam String address) {
        log.info(" *****************************    NAVER Geocoding API START    *****************************");

        String response = commonService.getCoordinatesFromNaverAPI(address);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/containers/coordinates")
    public ResponseEntity<ApiSuccessResponse<Object>> getContainersWithCoordinates() {
        log.info(" *****************************    Container Select START    *****************************");

        List<ContainerMapListDTO> containers = containerService.getContainersForMap();

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_SELECT_SUCCESS, containers);
    }

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