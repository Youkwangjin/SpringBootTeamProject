package com.acorn.api.dto.board;

import com.acorn.api.common.PaginationRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListDTO extends PaginationRequest {

    private String pageNo;

    private String searchType;

    private String searchName;

    private Integer boardId;

    private String boardTitle;

    private String boardWriter;

    private String boardContents;

    private String boardContentsText;

    private Integer boardHits;

    private LocalDateTime boardCreated;

    private LocalDateTime boardUpdated;

    @Override
    public String getPageNo() {
        return this.pageNo;
    }

    @Override
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public void setTotalCount(int totalCount) {
        super.setTotalCount(totalCount);
    }
}