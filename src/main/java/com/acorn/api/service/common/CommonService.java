package com.acorn.api.service.common;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface CommonService {

    String imageUpload(MultipartFile file) throws IOException;
}
