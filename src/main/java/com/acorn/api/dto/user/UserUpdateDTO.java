package com.acorn.api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    private Integer userId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\":;'<>?,./]).{10,}$")
    private String userPassword;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String userNm;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$")
    private String userTel;

    private String userAddr;
}