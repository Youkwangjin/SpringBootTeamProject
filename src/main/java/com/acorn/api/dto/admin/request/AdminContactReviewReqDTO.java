package com.acorn.api.dto.admin.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminContactReviewReqDTO {

    @NotNull
    @Positive
    private Integer contactId;
}