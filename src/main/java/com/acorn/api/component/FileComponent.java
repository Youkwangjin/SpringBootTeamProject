package com.acorn.api.component;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.exception.global.AcontainerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileComponent {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${profiles.name.local}")
    private String LOCAL;

    @Value("${profiles.name.prod}")
    private String PROD;

    public void upload(String directory, String fileName, MultipartFile multipartFile) {
        try {
            if (StringUtils.equals(profile, LOCAL)) {
                File uploadPath = new File(directory);
                if (!uploadPath.exists()) {
                    throw new AcontainerException(ApiErrorCode.FILE_PATH_ERROR);
                }
                File uploadFile = new File(directory + fileName);
                multipartFile.transferTo(uploadFile);

            } else if (StringUtils.equalsIgnoreCase(profile, PROD)) {
                // 구현 필요.
                throw new AcontainerException(ApiErrorCode.S3_FILE_UPLOAD_ERROR);

            } else {
                throw new AcontainerException(ApiErrorCode.FILE_UPLOAD_ERROR);
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

        } else if (StringUtils.equalsIgnoreCase(profile, PROD)) {
            // 구현 필요.
            throw new AcontainerException(ApiErrorCode.S3_FILE_DELETE_ERROR);
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

            } else if (StringUtils.equalsIgnoreCase(profile, PROD)) {
                throw new AcontainerException(ApiErrorCode.S3_FILE_DOWNLOAD_ERROR);

            } else {
                throw new AcontainerException(ApiErrorCode.FILE_DOWNLOAD_ERROR);
            }
        } catch (IOException e) {
            throw new AcontainerException(ApiErrorCode.FILE_DOWNLOAD_ERROR);
        }
    }
}