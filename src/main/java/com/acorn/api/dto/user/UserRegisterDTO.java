package com.acorn.api.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserRegisterDTO {

    private int userId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String userEmail;

    @NotBlank
    @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\":;'<>?,./]).{10,}$")
    private String userPassword;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$")
    private String userDisplayName;

    @NotBlank
    @Pattern(regexp = "^01(0|1|[6-9])[0-9]{3,4}[0-9]{4}$")
    private String userTel;

    private String userAddr;

}
