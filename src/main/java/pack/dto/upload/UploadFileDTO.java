package pack.dto.upload;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UploadFileDTO {
    private MultipartFile file, randomFilename;
}