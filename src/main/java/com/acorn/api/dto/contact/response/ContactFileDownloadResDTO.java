package com.acorn.api.dto.contact.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactFileDownloadResDTO {

    private String originalFileName;

    private byte[] fileBytes;
}