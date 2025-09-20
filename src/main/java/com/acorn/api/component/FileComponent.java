package com.acorn.api.component;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.exception.global.AcontainerException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FileComponent {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${profiles.name.local}")
    private String LOCAL;

    @Value("${profiles.name.prod}")
    private String PROD;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public void upload(String directory, String fileName, MultipartFile multipartFile) {
        try {
            if (StringUtils.equals(profile, LOCAL)) {
                File uploadPath = new File(directory);
                if (!uploadPath.exists()) {
                    throw new AcontainerException(ApiErrorCode.FILE_PATH_ERROR);
                }
                File uploadFile = new File(directory + fileName);
                multipartFile.transferTo(uploadFile);

            } else if (StringUtils.equals(profile, PROD)) {
                PutObjectRequest putReq = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

                s3Client.putObject(putReq, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

            } else {
                throw new AcontainerException(ApiErrorCode.PROFILE_NOT_FOUND);
            }

        } catch (IOException e) {
            throw new AcontainerException(ApiErrorCode.FILE_PATH_ERROR);
        }
    }

    public void delete(String directory, String fileName) {
        if (StringUtils.equals(profile, LOCAL)) {
            final String filePath = directory + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                if (!file.delete()) {
                    throw new AcontainerException(ApiErrorCode.FILE_DELETE_ERROR);
                }

            } else {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }

        } else if (StringUtils.equals(profile, PROD)) {
            DeleteObjectRequest deleteReq = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();
            s3Client.deleteObject(deleteReq);

        } else {
            throw new AcontainerException(ApiErrorCode.PROFILE_NOT_FOUND);
        }
    }

    public byte[] download(String directory, String fileName) {
        try {
            if (StringUtils.equals(profile, LOCAL)) {
                File file = new File(directory + fileName);
                if (!file.exists()) {
                    throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
                }

                return FileCopyUtils.copyToByteArray(file);

            } else if (StringUtils.equals(profile, PROD)) {

                GetObjectRequest getReq = GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

                ResponseBytes<GetObjectResponse> resBytes = s3Client.getObjectAsBytes(getReq);

                return  resBytes.asByteArray();

            } else {
                throw new AcontainerException(ApiErrorCode.PROFILE_NOT_FOUND);
            }

        } catch (IOException e) {
            throw new AcontainerException(ApiErrorCode.FILE_DOWNLOAD_ERROR);
        }
    }
}