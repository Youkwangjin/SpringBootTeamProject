package com.acorn.api.dto.contact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ContactSaveDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String contactTitle;

    @NotBlank
    private String contactContents;

    private List<MultipartFile> contactFiles;
}