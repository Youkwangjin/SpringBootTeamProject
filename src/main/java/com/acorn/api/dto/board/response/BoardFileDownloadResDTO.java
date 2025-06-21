package com.acorn.api.dto.board.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardFileDownloadResDTO {

    private String originalFileName;

    private byte[] fileBytes;
}