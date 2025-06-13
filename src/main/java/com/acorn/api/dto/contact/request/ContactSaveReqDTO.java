package com.acorn.api.dto.contact.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContactSaveReqDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String contactTitle;

    @NotBlank
    private String contactContents;

    private List<MultipartFile> contactFiles;
}