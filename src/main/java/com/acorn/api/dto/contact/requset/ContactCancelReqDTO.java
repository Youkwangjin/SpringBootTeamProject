package com.acorn.api.dto.contact.requset;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactCancelReqDTO {

    @NotNull
    @Positive
    private Integer contactId;
}