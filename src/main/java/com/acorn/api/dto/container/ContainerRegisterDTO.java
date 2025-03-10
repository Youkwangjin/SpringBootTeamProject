package com.acorn.api.dto.container;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ContainerRegisterDTO {

    @NotBlank
    @Size(max = 100)
    private String containerName;

    @NotBlank
    private String containerAddr;

    @NotNull
    @Digits(integer = 3, fraction = 7)
    private BigDecimal containerLatitude;

    @NotNull
    @Digits(integer = 3, fraction = 7)
    private BigDecimal containerLongitude;

    @NotBlank
    private String containerContents;

    @NotNull
    @Min(value = 1)
    private Integer containerSize;

    @NotNull
    @Min(value = 0)
    private Integer containerPrice;

    @NotNull
    private Integer containerOwnerId;

    @Size(max = 5)
    private List<MultipartFile> containerFiles;
}