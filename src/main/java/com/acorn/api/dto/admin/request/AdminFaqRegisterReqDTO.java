package com.acorn.api.dto.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminFaqRegisterReqDTO {

    @NotBlank
    @NotBlank
    @Size(min = 1, max = 50)
    private String faqTitle;

    @NotBlank
    private String faqContents;
}