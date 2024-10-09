package com.acorn.api.service.common.impl;

import com.acorn.api.service.common.CommonService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${image.upload.path}")
    private String editorImageUploadPath;

    private static final String[] permitImageExtension = {"jpg", "jpeg", "bmp", "png", "gif"};

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

        File localFile = new File(editorImageUploadPath, imageFileName);
        file.transferTo(localFile);

        return localFile.getPath();
    }
}
