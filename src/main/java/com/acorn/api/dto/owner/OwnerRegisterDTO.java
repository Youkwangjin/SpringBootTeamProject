package com.acorn.api.dto.owner;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OwnerRegisterDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String ownerEmail;

    @NotBlank
    @Pattern(regexp = "^(\\d{3}-\\d{2}-\\d{5}|\\d{10})$")
    private String ownerBusinessNum;

    @NotBlank
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\":;'<>?,./]).{10,}$")
    private String ownerPassword;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String ownerName;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$")
    private String ownerTel;

    @NotBlank
    @Pattern(regexp = "^[0-9\\p{L}&\\-'.\\s]{2,50}$")
    private String ownerCompanyName;

    private String ownerAddr;

}
