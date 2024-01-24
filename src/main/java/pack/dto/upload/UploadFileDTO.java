package pack.dto.upload;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;


@Data
@Component
public class UploadFileDTO {
    private MultipartFile file, randomFilename;
}