package com.acorn.api.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardListDTO {

    private int pageNo;

    private String searchName;

    private String searchType;
}
