package com.acorn.api.dto.admin.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminFaqUpdateReqDTO {

    @NotNull
    @Positive
    private Integer faqId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String faqTitle;

    @NotBlank
    private String faqContents;
}