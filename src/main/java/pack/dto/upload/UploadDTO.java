package pack.dto.upload;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadDTO {
    private String myName;
    private MultipartFile myFile;
}

