package com.acorn.api.component;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.exception.global.AcontainerException;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileComponent {
    public void upload(String directory, String fileName, MultipartFile multipartFile) {
        try {
            File uploadPath = new File(directory);
            if (!uploadPath.exists()) {
                throw new AcontainerException(ApiErrorCode.FILE_PATH_ERROR);
            }
            File uploadFile = new File(directory + fileName);
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new AcontainerException(ApiErrorCode.FILE_PATH_ERROR);
        }
    }

    public void delete(String directory, String fileName) {
        final String filePath = directory + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new AcontainerException(ApiErrorCode.FILE_DELETE_ERROR);
            }
        } else {
            throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
        }
    }

    public byte[] download(String directory, String fileName) {
        try {
            File file = new File(directory + fileName);
            if (!file.exists()) {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }
            return FileCopyUtils.copyToByteArray(file);
        } catch (IOException e) {
            throw new AcontainerException(ApiErrorCode.FILE_DOWNLOAD_ERROR);
        }
    }
}