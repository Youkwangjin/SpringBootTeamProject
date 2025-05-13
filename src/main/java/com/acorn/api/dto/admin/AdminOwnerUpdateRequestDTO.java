package com.acorn.api.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOwnerUpdateRequestDTO {

    @NotNull
    @Positive
    private Integer ownerId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String ownerEmail;

    @NotBlank
    @Pattern(regexp = "^(\\d{3}-\\d{2}-\\d{5}|\\d{10})$")
    private String ownerBusinessNum;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String ownerNm;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$")
    private String ownerTel;

    @NotBlank
    @Pattern(regexp = "^[0-9\\p{L}&\\-'.\\s]{2,50}$")
    private String ownerCompanyName;

    private String ownerAddr;
}