package pack.dto.upload;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadDTO {
    private String myName;
    private MultipartFile myFile;
}

