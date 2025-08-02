package com.acorn.api.service.common.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    @Value("${image.upload.path}")
    private String editorImageUploadPath;

    @Value("${image.access.path}")
    private String imageAccessPath;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${profiles.name.local}")
    private String LOCAL;

    @Value("${profiles.name.prod}")
    private String PROD;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final WebClient webClient;

    private final S3Client s3Client;

    private static final String[] permitImageExtension = {"jpg", "jpeg", "bmp", "png", "gif"};

    @Override
    public String getCoordinatesFromNaverAPI(String address) {
        if (StringUtils.isBlank(address)) {
            throw new AcontainerException(ApiValidationErrorCode.ADDRESS_FORMAT_ERROR);
        }

        try {
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/map-geocode/v2/geocode")
                            .queryParam("query", address)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("NAVER Geocoding API Response: {}", response);
            return response;

        } catch (WebClientResponseException e) {
            log.error("NAVER Geocoding API 오류 - 상태 코드: {}, 메시지: {}", e.getStatusCode(), e.getMessage());
            throw new AcontainerException(ApiErrorCode.NAVER_API_CALL_ERROR);

        } catch (Exception e) {
            log.error("NAVER Geocoding API 호출 예외: {}", e.getMessage(), e);
            throw new AcontainerException(ApiErrorCode.NAVER_API_INVALID_RESPONSE);
        }
    }

    @Override
    public String imageUpload(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            throw new IOException("파일이 비어 있습니다.");
        }

        String imageFileExt = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();

        if(!Arrays.asList(permitImageExtension).contains(imageFileExt)) {
            throw new IOException("업로드 불가능한 이미지 포맷입니다: " + imageFileExt);
        }

        String imageFileName = String.format("%s.%s", UUID.randomUUID().toString().replaceAll("-", ""), imageFileExt);

        try {
            if (StringUtils.equals(profile, LOCAL)) {
                File localFile = new File(editorImageUploadPath, imageFileName);
                file.transferTo(localFile);

            } else if (StringUtils.equals(profile, PROD)) {
                String key = editorImageUploadPath + imageFileName;

                PutObjectRequest putReq = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();

                s3Client.putObject(putReq, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            } else {
                throw new AcontainerException(ApiErrorCode.PROFILE_NOT_FOUND);
            }

        } catch (S3Exception e) {
            log.error("S3 업로드 실패 [bucket={}, key={}, code={}, msg={}]", bucket, imageFileName, e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage());
            throw new AcontainerException(ApiErrorCode.S3_FILE_UPLOAD_ERROR);

        } catch (IOException | SdkClientException e) {
            log.error("파일 업로드 예외 [profile={}, key={}, error={}]", profile, imageFileName, e.getMessage());
            throw new AcontainerException(ApiErrorCode.S3_FILE_UPLOAD_ERROR);
        }

        return imageAccessPath + imageFileName;
    }
}